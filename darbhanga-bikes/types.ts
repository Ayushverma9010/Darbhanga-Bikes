
export type UserRole = 'customer' | 'rider' | 'admin';
export type RiderStatus = 'Pending' | 'Active' | 'Suspended';

export interface Location {
  lat: number;
  lng: number;
  address: string;
}

export interface UserProfile {
  id: string;
  name: string;
  phone: string;
  password?: string;
  role: UserRole;
  isActive: boolean;
  riderStatus?: RiderStatus;
  bikeNumber?: string;
  licensePhoto?: string;
  earnings?: number;
  commissionOwed?: number;
  referralCode: string;
  referredBy?: string;
  hasFreeRide?: boolean;
  referralEarnings?: number;
  joinDate: number;
  completedRidesCount: number;
  currentLat?: number;
  currentLng?: number;
}

export enum RideStatus {
  REQUESTED = 'REQUESTED',
  OFFERED = 'OFFERED',
  ASSIGNED = 'ASSIGNED',
  ARRIVED = 'ARRIVED',
  STARTED = 'STARTED',
  COMPLETED = 'COMPLETED',
  CANCELLED = 'CANCELLED'
}

export interface Message {
  id: string;
  senderId: string;
  senderRole: UserRole;
  text: string;
  timestamp: number;
}

export interface Ride {
  id: string;
  customerId: string;
  customerName?: string;
  customerPhone?: string;
  riderId?: string;
  riderName?: string;
  riderPhone?: string;
  riderBike?: string;
  pickup: Location;
  drop: Location;
  distance: number;
  baseFare: number;
  isFree?: boolean;
  status: RideStatus;
  createdAt: number;
  arrivedAt?: number;
  startedAt?: number;
  completedAt?: number;
  
  // Post-ride collection data
  actualFareCollected?: number;
  tipAmount?: number;
  waitingCharges?: number;
  
  chat: Message[];
  ridePin: string;
  offeredToQueue: string[];
  currentOfferedRiderId?: string;
  offeredAt?: number;
}

export interface AppState {
  currentUser: UserProfile | null;
  users: UserProfile[];
  rides: Ride[];
  commissionRate: number;
  searchRadiusKm: number;
}
