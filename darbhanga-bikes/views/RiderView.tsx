
import React, { useState, useEffect } from 'react';
import { Power, MapPin, Navigation, User, Bike, Clock, Phone, MessageSquare, CheckCircle2, DollarSign } from 'lucide-react';
import { UserProfile, Ride, RideStatus } from '../types';
import { formatCurrency } from '../utils';

interface RiderViewProps {
  rider: UserProfile;
  rides: Ride[];
  onAcceptRide: (rideId: string) => void;
  onRejectRide: (rideId: string) => void;
  onArrive: (rideId: string) => void;
  onStart: (rideId: string) => void;
  onComplete: (rideId: string, collected: number, tip: number, waiting: number) => void;
  onSendMessage: (rideId: string, text: string) => void;
  onToggleActive: () => void;
}

const RiderView: React.FC<RiderViewProps> = ({ rider, rides, onAcceptRide, onRejectRide, onArrive, onStart, onComplete, onSendMessage, onToggleActive }) => {
  const [inputPin, setInputPin] = useState('');
  const [waitingElapsed, setWaitingElapsed] = useState(0);
  const [collectData, setCollectData] = useState({ fare: '', tip: '' });
  
  const offeredRide = rides.find(r => r.status === RideStatus.OFFERED && r.currentOfferedRiderId === rider.id);
  const activeRide = rides.find(r => r.riderId === rider.id && [RideStatus.ASSIGNED, RideStatus.ARRIVED, RideStatus.STARTED].includes(r.status));

  // Waiting Charge Timer logic
  useEffect(() => {
    let interval: any;
    if (activeRide?.status === RideStatus.ARRIVED) {
      interval = setInterval(() => {
        setWaitingElapsed(prev => prev + 1);
      }, 1000);
    } else {
      setWaitingElapsed(0);
    }
    return () => clearInterval(interval);
  }, [activeRide?.status]);

  const waitingCharges = waitingElapsed > 180 ? Math.floor((waitingElapsed - 180) / 180) * 5 + 5 : 0;

  if (rider.riderStatus === 'Pending') {
    return (
      <div className="p-10 flex flex-col items-center justify-center h-full text-center space-y-6">
         <Clock size={64} className="text-blue-500 animate-pulse" />
         <h2 className="text-3xl font-black uppercase italic tracking-tighter">Verifying Captain</h2>
         <p className="text-gray-400 font-bold uppercase text-[10px] tracking-widest">Your documents are under review by Ayush Admin.</p>
      </div>
    );
  }

  return (
    <div className="p-6 space-y-6 pb-32">
      <div className="bg-white rounded-[2.5rem] p-8 shadow-xl border-2 border-gray-50 flex items-center justify-between">
        <div className="flex items-center gap-4">
          <div className="bg-black p-3 rounded-2xl"><User className="text-[#FFDD00]" size={24}/></div>
          <div>
            <p className="font-black text-xl uppercase italic tracking-tighter">{rider.name}</p>
            <p className="text-[9px] font-black uppercase tracking-widest text-gray-400">{rider.bikeNumber}</p>
          </div>
        </div>
        <button onClick={onToggleActive} className={`p-4 rounded-2xl font-black uppercase text-[9px] flex items-center gap-2 ${rider.isActive ? 'bg-green-500 text-white shadow-lg' : 'bg-gray-100 text-gray-400'}`}>
          <Power size={14}/> {rider.isActive ? 'Online' : 'Offline'}
        </button>
      </div>

      {offeredRide && (
        <div className="bg-[#FFDD00] rounded-[3rem] p-10 shadow-2xl space-y-6 animate-in zoom-in-95 border-4 border-black">
          <p className="text-6xl font-black italic tracking-tighter text-black">{formatCurrency(offeredRide.baseFare)}</p>
          <div className="space-y-4">
            <div className="flex items-center gap-3"><MapPin size={20}/><p className="text-xs font-black uppercase truncate flex-1">{offeredRide.pickup.address}</p></div>
            <div className="flex items-center gap-3"><Navigation size={20}/><p className="text-xs font-black uppercase truncate flex-1">{offeredRide.drop.address}</p></div>
          </div>
          <div className="flex gap-4">
            <button onClick={() => onRejectRide(offeredRide.id)} className="flex-1 bg-white/20 py-5 rounded-3xl font-black uppercase text-[10px]">Reject</button>
            <button onClick={() => onAcceptRide(offeredRide.id)} className="flex-[2] bg-black text-[#FFDD00] py-5 rounded-3xl font-black uppercase text-lg shadow-xl">Accept</button>
          </div>
        </div>
      )}

      {activeRide && (
        <div className="bg-black text-white rounded-[3rem] p-8 shadow-2xl space-y-8 animate-in slide-in-from-bottom border-4 border-[#FFDD00]">
          <div className="flex justify-between items-center">
             <h3 className="text-3xl font-black uppercase italic tracking-tighter text-[#FFDD00]">{activeRide.status}</h3>
             <a href="mailto:ayushverma9010@gmail.com" className="bg-white/10 p-4 rounded-2xl"><Phone size={24}/></a>
          </div>

          <div className="space-y-4">
             <div className="flex items-center gap-4 bg-white/10 p-5 rounded-2xl">
               <MapPin className="text-[#FFDD00]"/>
               <div><p className="text-[8px] font-black uppercase text-gray-500">Target</p><p className="text-xs font-bold uppercase truncate">{activeRide.status === 'ASSIGNED' ? activeRide.pickup.address : activeRide.drop.address}</p></div>
             </div>
             {activeRide.status === RideStatus.ARRIVED && (
               <div className="bg-[#FFDD00]/10 border-2 border-[#FFDD00] p-6 rounded-2xl text-center">
                  <p className="text-[10px] font-black uppercase text-[#FFDD00] tracking-widest mb-2">Wait Timer</p>
                  <p className="text-4xl font-black tabular-nums">{Math.floor(waitingElapsed / 60)}:{(waitingElapsed % 60).toString().padStart(2, '0')}</p>
                  <p className="text-xs font-black uppercase mt-2 text-white">Charge: {formatCurrency(waitingCharges)} (100% Admin)</p>
               </div>
             )}
          </div>

          {activeRide.status === RideStatus.ASSIGNED && (
            <button onClick={() => onArrive(activeRide.id)} className="w-full bg-[#FFDD00] text-black py-6 rounded-3xl font-black uppercase text-xl shadow-xl">Arrived at Pickup</button>
          )}

          {activeRide.status === RideStatus.ARRIVED && (
            <div className="space-y-4">
               <input 
                 type="tel" maxLength={4}
                 className="w-full bg-white/5 border-2 border-[#FFDD00] rounded-2xl p-6 text-center text-4xl font-black tracking-[0.4em] outline-none text-[#FFDD00]"
                 placeholder="PIN" value={inputPin} onChange={e => setInputPin(e.target.value)}
               />
               <button onClick={() => { if(inputPin === activeRide.ridePin) onStart(activeRide.id); else alert("Wrong PIN"); }} className="w-full bg-[#FFDD00] text-black py-6 rounded-3xl font-black uppercase text-xl">Start Ride</button>
            </div>
          )}

          {activeRide.status === RideStatus.STARTED && (
            <div className="space-y-4">
               <div className="grid grid-cols-2 gap-4">
                 <input type="number" placeholder="Fare Collected" className="w-full bg-white/10 p-5 rounded-2xl font-black outline-none border-2 border-transparent focus:border-[#FFDD00]" value={collectData.fare} onChange={e => setCollectData({...collectData, fare: e.target.value})} />
                 <input type="number" placeholder="Tip (Optional)" className="w-full bg-white/10 p-5 rounded-2xl font-black outline-none border-2 border-transparent focus:border-[#FFDD00]" value={collectData.tip} onChange={e => setCollectData({...collectData, tip: e.target.value})} />
               </div>
               <button onClick={() => onComplete(activeRide.id, Number(collectData.fare), Number(collectData.tip), waitingCharges)} className="w-full bg-green-500 text-white py-6 rounded-3xl font-black uppercase text-xl shadow-xl flex items-center justify-center gap-3">
                 <CheckCircle2 /> Finish Mission
               </button>
            </div>
          )}
        </div>
      )}

      <div className="bg-gray-100 p-8 rounded-[2.5rem] space-y-4">
         <p className="text-[9px] font-black uppercase tracking-widest text-gray-400">Help & Support</p>
         <div className="space-y-1">
            <p className="text-xs font-bold">ayushverma9010@gmail.com</p>
            <p className="text-xs font-bold">+91 9060485528</p>
         </div>
      </div>
    </div>
  );
};

export default RiderView;
