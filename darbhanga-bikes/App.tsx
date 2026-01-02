
import React, { useState, useEffect } from 'react';
import { AppState, UserRole, UserProfile, Ride, Location, RideStatus, Message } from './types';
import { INITIAL_RIDER_DATA, INITIAL_CUSTOMER_DATA, ADMIN_USER } from './constants';
import Layout from './components/Layout';
import CustomerView from './views/CustomerView';
import RiderView from './views/RiderView';
import AdminView from './views/AdminView';
import { haversineDistance, generateReferralCode, calculateFare } from './utils';
import { Bike, Loader2 } from 'lucide-react';

const App: React.FC = () => {
  const [state, setState] = useState<AppState>(() => {
    const saved = localStorage.getItem('darbhanga_bikes_v7');
    const baseUsers = [...INITIAL_RIDER_DATA, ...INITIAL_CUSTOMER_DATA, ADMIN_USER];

    if (saved) {
      try {
        const parsed = JSON.parse(saved);
        const combinedUsers = [...baseUsers];
        parsed.users.forEach((u: UserProfile) => {
          if (!combinedUsers.find(cu => cu.id === u.id)) combinedUsers.push(u);
        });
        const finalUsers = combinedUsers.map(u => u.id === 'admin' ? ADMIN_USER : u);
        return { 
          ...parsed, 
          users: finalUsers,
          currentUser: parsed.currentUser ? finalUsers.find(u => u.id === parsed.currentUser.id) || null : null
        };
      } catch (e) {
        localStorage.removeItem('darbhanga_bikes_v7');
      }
    }

    return {
      currentUser: null,
      users: baseUsers,
      rides: [],
      commissionRate: 0.1,
      searchRadiusKm: 3
    };
  });

  const [view, setView] = useState<'home' | 'login' | 'app'>(state.currentUser ? 'app' : 'home');
  const [loginRole, setLoginRole] = useState<UserRole>('customer');
  const [loginId, setLoginId] = useState('');
  const [loginPass, setLoginPass] = useState('');
  const [isLoggingIn, setIsLoggingIn] = useState(false);

  useEffect(() => {
    localStorage.setItem('darbhanga_bikes_v7', JSON.stringify(state));
  }, [state]);

  // Ride Offer Management
  useEffect(() => {
    const timer = setInterval(() => {
      setState(prev => {
        let changed = false;
        const now = Date.now();
        const nextRides = prev.rides.map(r => {
          if (r.status === RideStatus.OFFERED && (now - (r.offeredAt || 0)) > 15000) {
            changed = true;
            const queue = r.offeredToQueue || [];
            const idx = queue.indexOf(r.currentOfferedRiderId || '') + 1;
            if (idx < queue.length) {
              return { ...r, currentOfferedRiderId: queue[idx], offeredAt: now };
            } else {
              return { ...r, status: RideStatus.CANCELLED };
            }
          }
          return r;
        });
        return changed ? { ...prev, rides: nextRides } : prev;
      });
    }, 1000);
    return () => clearInterval(timer);
  }, []);

  const handleLogin = (identifier: string, passwordOrOtp?: string) => {
    setIsLoggingIn(true);
    // Simulate minor network delay for feedback
    setTimeout(() => {
      const user = state.users.find(u => {
        const matchRole = u.role === loginRole;
        const matchId = u.phone.trim().toLowerCase() === identifier.trim().toLowerCase();
        if (loginRole === 'rider') return matchRole && matchId && passwordOrOtp === '1234';
        if (loginRole === 'admin') return matchRole && matchId && u.password === passwordOrOtp;
        return matchRole && matchId;
      });

      if (user) {
        setState(prev => ({ ...prev, currentUser: user }));
        setView('app');
        setIsLoggingIn(false);
      } else {
        alert("Invalid login. For customer try: 0000000000. For rider try 1234 PIN.");
        setIsLoggingIn(false);
      }
    }, 400);
  };

  const handleLogout = () => {
    setState(prev => ({ ...prev, currentUser: null }));
    setLoginId('');
    setLoginPass('');
    setView('home');
  };

  const handleRegisterRider = (data: any) => {
    const referrer = state.users.find(u => u.referralCode === data.referralCode && u.role === 'rider');
    const bonus = referrer ? Math.floor(Math.random() * 50) + 1 : 0;

    const newUser: UserProfile = {
      id: `rider_${Date.now()}`,
      name: data.name,
      phone: data.phone,
      bikeNumber: data.bikeNumber,
      licensePhoto: data.licensePhoto,
      role: 'rider',
      isActive: false,
      riderStatus: 'Pending',
      earnings: bonus,
      commissionOwed: 0,
      referralCode: generateReferralCode(data.name),
      referredBy: referrer?.id,
      joinDate: Date.now(),
      completedRidesCount: 0,
      referralEarnings: bonus,
      currentLat: 26.1542,
      currentLng: 85.8918
    };

    setState(prev => ({
      ...prev,
      users: prev.users.map(u => u.id === referrer?.id ? { ...u, earnings: (u.earnings || 0) + bonus, referralEarnings: (u.referralEarnings || 0) + bonus } : u).concat(newUser)
    }));
    alert("Profile submitted for approval!");
    setView('home');
  };

  const handleRequestRide = (pickup: Location, drop: Location, distance: number, fare: number, phone: string) => {
    const pin = Math.floor(1000 + Math.random() * 9000).toString();
    const activeRiders = state.users
      .filter(u => u.role === 'rider' && u.riderStatus === 'Active' && u.isActive)
      .map(u => ({ id: u.id, dist: haversineDistance(pickup.lat, pickup.lng, u.currentLat || 26.15, u.currentLng || 85.89) }))
      .filter(r => r.dist <= state.searchRadiusKm)
      .sort((a, b) => a.dist - b.dist)
      .map(r => r.id);

    const user = state.currentUser!;
    const isFree = !!user.hasFreeRide;

    const newRide: Ride = {
      id: `ride_${Date.now()}`,
      customerId: user.id,
      customerName: user.name,
      customerPhone: phone,
      pickup,
      drop,
      distance,
      baseFare: isFree ? 0 : fare,
      isFree,
      status: activeRiders.length > 0 ? RideStatus.OFFERED : RideStatus.REQUESTED,
      createdAt: Date.now(),
      ridePin: pin,
      offeredToQueue: activeRiders,
      currentOfferedRiderId: activeRiders[0],
      offeredAt: Date.now(),
      chat: []
    };

    setState(prev => ({ 
      ...prev, 
      rides: [...prev.rides, newRide],
      users: prev.users.map(u => u.id === user.id ? { ...u, hasFreeRide: false } : u)
    }));
  };

  const handleCompleteRide = (id: string, actualCollected: number, tip: number, waiting: number) => {
    const ride = state.rides.find(r => r.id === id);
    if (!ride) return;

    setState(prev => {
      const customer = prev.users.find(u => u.id === ride.customerId);
      let updatedUsers = prev.users.map(u => {
        if (u.id === ride.riderId) {
          const totalEarned = actualCollected + tip;
          const comm = (totalEarned * prev.commissionRate) + waiting;
          return { ...u, earnings: (u.earnings || 0) + totalEarned, commissionOwed: (u.commissionOwed || 0) + comm };
        }
        if (u.id === ride.customerId) {
          return { ...u, completedRidesCount: (u.completedRidesCount || 0) + 1 };
        }
        return u;
      });

      // Referral logic
      if (customer && (customer.completedRidesCount || 0) === 0) {
        const isSameDay = new Date(customer.joinDate).toDateString() === new Date().toDateString();
        if (isSameDay && customer.referredBy) {
          updatedUsers = updatedUsers.map(u => u.id === customer.referredBy ? { ...u, hasFreeRide: true } : u);
        }
      }

      return {
        ...prev,
        rides: prev.rides.map(r => r.id === id ? { 
          ...r, 
          status: RideStatus.COMPLETED, 
          completedAt: Date.now(), 
          actualFareCollected: actualCollected, 
          tipAmount: tip, 
          waitingCharges: waiting 
        } : r),
        users: updatedUsers
      };
    });
  };

  if (view !== 'app' || !state.currentUser) {
    return (
      <div className="min-h-screen bg-[#FFDD00] flex flex-col items-center justify-center p-8 text-black">
        <div className="bg-black p-4 rounded-3xl shadow-2xl mb-6">
          <Bike size={48} className="text-[#FFDD00] animate-bounce" />
        </div>
        <h1 className="text-4xl font-black italic uppercase tracking-tighter mb-8 text-center drop-shadow-sm">Darbhanga Bikes</h1>
        
        <div className="w-full max-w-xs space-y-4">
          <div className="flex gap-2 mb-2 p-1 bg-black/5 rounded-2xl">
            {(['customer', 'rider', 'admin'] as UserRole[]).map(r => (
              <button 
                key={r} 
                onClick={() => {
                  setLoginRole(r);
                  setLoginId('');
                  setLoginPass('');
                }}
                className={`flex-1 py-3 rounded-xl font-black uppercase text-[10px] transition-all active:scale-95 ${loginRole === r ? 'bg-black text-[#FFDD00] shadow-lg' : 'text-black/60 hover:text-black'}`}
              >
                {r}
              </button>
            ))}
          </div>
          
          <div className="space-y-3">
            <input 
              type="text" 
              placeholder={loginRole === 'admin' ? "Admin Email" : "Phone Number"} 
              className="w-full bg-white rounded-2xl px-6 py-5 font-bold outline-none border-2 border-transparent focus:border-black shadow-sm transition-all"
              value={loginId}
              onChange={(e) => setLoginId(e.target.value)}
            />
            {(loginRole === 'rider' || loginRole === 'admin') && (
              <input 
                type="password" 
                placeholder={loginRole === 'rider' ? "PIN (1234)" : "Password"} 
                className="w-full bg-white rounded-2xl px-6 py-5 font-bold outline-none border-2 border-transparent focus:border-black shadow-sm transition-all"
                value={loginPass}
                onChange={(e) => setLoginPass(e.target.value)}
              />
            )}
          </div>
          
          <button 
            disabled={isLoggingIn || !loginId}
            onClick={() => handleLogin(loginId, loginPass)}
            className="w-full bg-black text-[#FFDD00] py-5 rounded-2xl font-black uppercase text-lg shadow-xl active:scale-95 transition-all flex items-center justify-center gap-2 disabled:opacity-50"
          >
            {isLoggingIn ? <Loader2 className="animate-spin" /> : `Enter ${loginRole}`}
          </button>
          
          {loginRole === 'rider' && (
            <button 
              onClick={() => {
                const name = prompt("Enter Full Name:");
                const phone = prompt("Enter Mobile Number:");
                const bike = prompt("Enter Bike Number (e.g. BR 07 X 1234):");
                if(name && phone && bike) handleRegisterRider({ name, phone, bikeNumber: bike });
              }}
              className="w-full text-black font-black uppercase text-[10px] tracking-widest mt-4 py-2 opacity-60 hover:opacity-100 transition-opacity"
            >
              Become a Captain
            </button>
          )}
        </div>
      </div>
    );
  }

  return (
    <Layout role={state.currentUser.role} userName={state.currentUser.name} onLogout={handleLogout}>
      {state.currentUser.role === 'customer' && (
        <CustomerView 
          user={state.currentUser} 
          rides={state.rides} 
          onRequestRide={handleRequestRide} 
          onSendMessage={(id, txt) => setState(prev => ({ ...prev, rides: prev.rides.map(r => r.id === id ? { ...r, chat: [...r.chat, { id: Date.now().toString(), senderId: state.currentUser!.id, senderRole: 'customer', text: txt, timestamp: Date.now() }] } : r) }))}
          onCancelRide={(id) => setState(prev => ({ ...prev, rides: prev.rides.map(r => r.id === id ? { ...r, status: RideStatus.CANCELLED } : r) }))}
        />
      )}
      {state.currentUser.role === 'rider' && (
        <RiderView 
          rider={state.users.find(u => u.id === state.currentUser!.id)!} 
          rides={state.rides} 
          onAcceptRide={(id) => setState(prev => ({ ...prev, rides: prev.rides.map(r => r.id === id ? { ...r, status: RideStatus.ASSIGNED, riderId: state.currentUser!.id, riderName: state.currentUser!.name, riderPhone: state.currentUser!.phone, riderBike: state.currentUser!.bikeNumber } : r) }))}
          onRejectRide={(id) => setState(prev => ({ ...prev, rides: prev.rides.map(r => r.id === id ? { ...r, status: RideStatus.CANCELLED } : r) }))}
          onArrive={(id) => setState(prev => ({ ...prev, rides: prev.rides.map(r => r.id === id ? { ...r, status: RideStatus.ARRIVED, arrivedAt: Date.now() } : r) }))}
          onStart={(id) => setState(prev => ({ ...prev, rides: prev.rides.map(r => r.id === id ? { ...r, status: RideStatus.STARTED, startedAt: Date.now() } : r) }))}
          onComplete={handleCompleteRide}
          onSendMessage={(id, txt) => setState(prev => ({ ...prev, rides: prev.rides.map(r => r.id === id ? { ...r, chat: [...r.chat, { id: Date.now().toString(), senderId: state.currentUser!.id, senderRole: 'rider', text: txt, timestamp: Date.now() }] } : r) }))}
          onToggleActive={() => setState(prev => ({ ...prev, users: prev.users.map(u => u.id === state.currentUser!.id ? { ...u, isActive: !u.isActive } : u) }))}
        />
      )}
      {state.currentUser.role === 'admin' && (
        <AdminView 
          riders={state.users.filter(u => u.role === 'rider')} 
          rides={state.rides} 
          onUpdateStatus={(id, stat) => setState(prev => ({ ...prev, users: prev.users.map(u => u.id === id ? { ...u, riderStatus: stat } : u) }))}
          onMarkPaid={(id) => setState(prev => ({ ...prev, users: prev.users.map(u => u.id === id ? { ...u, commissionOwed: 0 } : u) }))}
        />
      )}
    </Layout>
  );
};

export default App;
