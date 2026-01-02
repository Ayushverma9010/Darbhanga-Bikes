
import React, { useState, useEffect, useRef } from 'react';
import { MapPin, Navigation, Bike, X, Loader2, Phone, MessageSquare, Send, User, Trash2, Gift, Ticket, Copy, Share2, ArrowRight } from 'lucide-react';
import { Location, Ride, RideStatus, Message, UserProfile } from '../types';
import { calculateFare, formatCurrency } from '../utils';
import { searchPlaces, PlaceResult } from '../geminiService';

interface CustomerViewProps {
  user: UserProfile;
  rides: Ride[];
  onRequestRide: (pickup: Location, drop: Location, distance: number, fare: number, phone: string) => void;
  onSendMessage: (rideId: string, text: string) => void;
  onCancelRide: (rideId: string) => void;
}

const CustomerView: React.FC<CustomerViewProps> = ({ user, rides, onRequestRide, onSendMessage, onCancelRide }) => {
  const [pickupQuery, setPickupQuery] = useState('');
  const [dropQuery, setDropQuery] = useState('');
  const [pickupSuggestions, setPickupSuggestions] = useState<PlaceResult[]>([]);
  const [dropSuggestions, setDropSuggestions] = useState<PlaceResult[]>([]);
  const [pickup, setPickup] = useState<Location | null>(null);
  const [drop, setDrop] = useState<Location | null>(null);
  const [bookingPhone, setBookingPhone] = useState(user.phone);
  const [showReferral, setShowReferral] = useState(false);
  const [showChat, setShowChat] = useState(false);

  const activeRide = rides.find(r => [RideStatus.REQUESTED, RideStatus.OFFERED, RideStatus.ASSIGNED, RideStatus.ARRIVED, RideStatus.STARTED].includes(r.status));

  useEffect(() => {
    const fetch = async () => {
      if (pickupQuery.length > 2 && !pickup) setPickupSuggestions(await searchPlaces(pickupQuery));
      if (dropQuery.length > 2 && !drop) setDropSuggestions(await searchPlaces(dropQuery));
    };
    const t = setTimeout(fetch, 500);
    return () => clearTimeout(t);
  }, [pickupQuery, dropQuery]);

  const distance = pickup && drop ? (Math.sqrt(Math.pow(pickup.lat - drop.lat, 2) + Math.pow(pickup.lng - drop.lng, 2)) * 111) : 0;
  const fare = calculateFare(distance);

  if (activeRide) {
    return (
      <div className="p-6 space-y-6 animate-in slide-in-from-bottom">
        <div className="bg-white rounded-[2.5rem] p-8 shadow-xl border-2 border-gray-50 text-center relative overflow-hidden">
          <div className="absolute top-0 left-0 w-full h-1 bg-[#FFDD00] animate-pulse" />
          <h2 className="text-3xl font-black uppercase italic tracking-tighter mb-2">
            {activeRide.status === RideStatus.STARTED ? 'On Mission' : 'Ride Tracking'}
          </h2>
          <p className="text-[10px] font-black uppercase text-gray-400 tracking-[0.3em]">{activeRide.status}</p>
        </div>

        {activeRide.riderId && (
          <div className="bg-white rounded-[2.5rem] p-8 shadow-xl border-2 border-[#FFDD00] space-y-6">
            <div className="flex items-center gap-5">
              <div className="bg-[#FFDD00] p-4 rounded-full shadow-lg"><User size={32} className="text-black"/></div>
              <div className="flex-1">
                <p className="text-xl font-black uppercase italic tracking-tighter">{activeRide.riderName}</p>
                <p className="text-[10px] font-black uppercase tracking-widest text-gray-400">{activeRide.riderBike}</p>
              </div>
              <button onClick={() => setShowChat(true)} className="bg-gray-50 p-4 rounded-2xl relative">
                <MessageSquare size={24} className="text-black" />
              </button>
            </div>
            
            <div className="flex items-center justify-between border-t pt-6">
              <div>
                <p className="text-[8px] font-black uppercase text-gray-400">Security PIN</p>
                <p className="text-4xl font-black tracking-[0.2em] text-[#FFDD00] drop-shadow-sm">{activeRide.ridePin}</p>
              </div>
              <div className="text-right">
                <p className="text-[8px] font-black uppercase text-gray-400">Total Fare</p>
                <p className="text-3xl font-black italic">{activeRide.isFree ? "FREE" : formatCurrency(activeRide.baseFare)}</p>
              </div>
            </div>
            <a href={`tel:${activeRide.riderPhone}`} className="w-full bg-black text-[#FFDD00] py-5 rounded-3xl font-black uppercase text-center flex items-center justify-center gap-3">
              <Phone size={18}/> Call Captain
            </a>
          </div>
        )}

        <div className="bg-gray-100 p-8 rounded-[2.5rem] text-center space-y-4">
           <p className="text-[9px] font-black uppercase tracking-widest text-gray-400">Support Email</p>
           <p className="text-xs font-bold text-black">ayushverma9010@gmail.com</p>
        </div>

        {[RideStatus.REQUESTED, RideStatus.OFFERED, RideStatus.ASSIGNED].includes(activeRide.status) && (
          <button 
            onClick={() => { if(confirm("Cancel?")) onCancelRide(activeRide.id); }}
            className="w-full py-4 text-red-500 font-black uppercase text-[10px] tracking-widest"
          >
            Cancel Ride
          </button>
        )}
      </div>
    );
  }

  return (
    <div className="p-6 space-y-6">
      <div onClick={() => setShowReferral(true)} className="bg-white border-2 border-green-100 rounded-[2rem] p-6 flex items-center justify-between cursor-pointer">
        <div className="flex items-center gap-4">
           <div className="bg-green-500 p-3 rounded-2xl text-white"><Gift size={20}/></div>
           <div>
              <p className="font-black text-xs uppercase italic tracking-tighter">Free Rides Available</p>
              <p className="text-[8px] font-bold uppercase text-green-600">Refer & Earn Now</p>
           </div>
        </div>
        <ArrowRight size={16} className="text-green-500" />
      </div>

      <div className="bg-white rounded-[2.5rem] p-8 space-y-6 shadow-xl border-2 border-gray-50">
        <h2 className="text-2xl font-black uppercase italic tracking-tighter mb-4">Book a Ride</h2>
        <div className="space-y-4">
          <div className="relative">
            <MapPin className="absolute left-6 top-1/2 -translate-y-1/2 text-green-500" size={20}/>
            <input 
              type="text" placeholder="Pickup location" 
              className="w-full bg-gray-50 rounded-2xl pl-16 pr-6 py-6 font-bold text-sm outline-none border-2 border-transparent focus:border-[#FFDD00]"
              value={pickupQuery} onChange={e => { setPickupQuery(e.target.value); setPickup(null); }}
            />
            {!pickup && pickupSuggestions.length > 0 && (
              <div className="absolute z-10 top-full left-0 right-0 bg-white shadow-2xl rounded-2xl mt-1 max-h-48 overflow-y-auto">
                {pickupSuggestions.map((s, i) => (
                  <button key={i} onClick={() => { setPickup(s); setPickupQuery(s.address); setPickupSuggestions([]); }} className="w-full text-left px-6 py-4 border-b last:border-0 font-bold text-[10px] uppercase hover:bg-gray-50">{s.address}</button>
                ))}
              </div>
            )}
          </div>
          <div className="relative">
            <Navigation className="absolute left-6 top-1/2 -translate-y-1/2 text-red-500" size={20}/>
            <input 
              type="text" placeholder="Drop destination" 
              className="w-full bg-gray-50 rounded-2xl pl-16 pr-6 py-6 font-bold text-sm outline-none border-2 border-transparent focus:border-[#FFDD00]"
              value={dropQuery} onChange={e => { setDropQuery(e.target.value); setDrop(null); }}
            />
            {!drop && dropSuggestions.length > 0 && (
              <div className="absolute z-10 top-full left-0 right-0 bg-white shadow-2xl rounded-2xl mt-1 max-h-48 overflow-y-auto">
                {dropSuggestions.map((s, i) => (
                  <button key={i} onClick={() => { setDrop(s); setDropQuery(s.address); setDropSuggestions([]); }} className="w-full text-left px-6 py-4 border-b last:border-0 font-bold text-[10px] uppercase hover:bg-gray-50">{s.address}</button>
                ))}
              </div>
            )}
          </div>
        </div>
      </div>

      {pickup && drop && (
        <div className="bg-[#FFDD00] text-black rounded-[2.5rem] p-8 shadow-2xl animate-in slide-in-from-bottom-5 space-y-6">
          <div className="flex justify-between items-center">
            <div className="flex items-center gap-4">
               <div className="bg-black p-3 rounded-2xl shadow-lg"><Bike className="text-[#FFDD00]" size={28}/></div>
               <div>
                  <p className="font-black text-xl uppercase italic tracking-tighter">Budget Bike</p>
                  <p className="text-[10px] font-bold uppercase tracking-widest opacity-40">{distance.toFixed(1)} KM Ride</p>
               </div>
            </div>
            <p className="text-4xl font-black italic">{user.hasFreeRide ? "FREE" : formatCurrency(fare)}</p>
          </div>
          <input 
            type="tel" maxLength={10} 
            className="w-full bg-white/40 rounded-2xl px-6 py-5 font-black outline-none border-2 border-transparent focus:border-black text-lg tracking-[0.1em]" 
            placeholder="Mobile Number" 
            value={bookingPhone} onChange={e => setBookingPhone(e.target.value.replace(/\D/g, ''))}
          />
          <button onClick={() => onRequestRide(pickup, drop, distance, fare, bookingPhone)} className="w-full bg-black text-[#FFDD00] py-6 rounded-3xl font-black uppercase text-xl shadow-xl active:scale-95 transition-all">Request Ride</button>
        </div>
      )}
    </div>
  );
};

export default CustomerView;
