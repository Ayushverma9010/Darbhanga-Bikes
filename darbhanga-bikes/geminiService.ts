
import { GoogleGenAI, Type } from "@google/genai";

export interface PlaceResult {
  address: string;
  lat: number;
  lng: number;
}

const searchCache = new Map<string, PlaceResult[]>();
const reverseCache = new Map<string, string>();

export const searchPlaces = async (query: string, userLat?: number, userLng?: number): Promise<PlaceResult[]> => {
  const trimmedQuery = query.trim().toLowerCase();
  if (!trimmedQuery || trimmedQuery.length < 2) return [];

  if (searchCache.has(trimmedQuery)) {
    return searchCache.get(trimmedQuery)!;
  }

  try {
    // Fix: Create Gemini instance right before the call as per guidelines
    const ai = new GoogleGenAI({ apiKey: process.env.API_KEY });
    const response = await ai.models.generateContent({
      model: "gemini-3-flash-preview",
      contents: `List 5 specific landmarks in Darbhanga, Bihar matching "${trimmedQuery}". Return ONLY a JSON array with keys: address, lat, lng. Be concise.`,
      config: {
        thinkingConfig: { thinkingBudget: 0 },
        temperature: 0.1,
        responseMimeType: "application/json",
        // Fix: Added responseSchema for robust JSON extraction
        responseSchema: {
          type: Type.ARRAY,
          items: {
            type: Type.OBJECT,
            properties: {
              address: { type: Type.STRING },
              lat: { type: Type.NUMBER },
              lng: { type: Type.NUMBER }
            },
            required: ["address", "lat", "lng"]
          }
        }
      },
    });

    // Fix: text is a property access, not a method call
    const text = response.text || "[]";
    const results = JSON.parse(text);
    if (Array.isArray(results)) {
      searchCache.set(trimmedQuery, results);
      return results;
    }
    return [];
  } catch (error) {
    console.error("Search Error:", error);
    // Fallback landmarks for Darbhanga to ensure responsiveness
    return [
      { address: "Tower Chowk, Darbhanga", lat: 26.1542, lng: 85.8918 },
      { address: "Donar Chowk, Darbhanga", lat: 26.1620, lng: 85.9010 },
      { address: "Laheriasarai Tower, Darbhanga", lat: 26.1245, lng: 85.8950 }
    ].filter(p => p.address.toLowerCase().includes(trimmedQuery));
  }
};

export const reverseGeocode = async (lat: number, lng: number): Promise<string> => {
  const key = `${lat.toFixed(4)},${lng.toFixed(4)}`;
  if (reverseCache.has(key)) return reverseCache.get(key)!;

  try {
    // Fix: Create Gemini instance right before the call
    const ai = new GoogleGenAI({ apiKey: process.env.API_KEY });
    const response = await ai.models.generateContent({
      model: "gemini-3-flash-preview",
      contents: `Identify the place at (${lat}, ${lng}) in Darbhanga. Return only a short address string.`,
      config: {
        thinkingConfig: { thinkingBudget: 0 },
        temperature: 0.1
      },
    });
    // Fix: text is a property
    const address = response.text?.trim() || "Darbhanga Local Area";
    reverseCache.set(key, address);
    return address;
  } catch (error) {
    return "Selected Point, Darbhanga";
  }
};
