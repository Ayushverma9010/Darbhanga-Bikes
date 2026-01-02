
import React, { useState } from 'react';
import { Users, Bike, DollarSign, FileText, X, AlertCircle, TrendingUp, Wallet } from 'lucide-react';
import { UserProfile, Ride, RiderStatus } from '../types';
import { formatCurrency } from '../utils';

interface AdminViewProps {
  riders: UserProfile[];
  rides: Ride[];
  onUpdateStatus: (riderId: string, status: RiderStatus) => void;
  onMarkPaid: (riderId: string) => void;
}

const AdminView: React.FC<AdminViewProps> = ({ riders, rides, onUpdateStatus, onMarkPaid }) => {
  const [activeTab, setActiveTab] = useState<'stats' | 'riders' | 'rides'>('stats');

  const totalCollected = rides.reduce((a, b) => a + (b.actualFareCollected || 0) + (b.tipAmount || 0), 0);
  const totalCommission = totalCollected * 0.1;
  const totalWaiting = rides.reduce((a, b) => a + (b.waitingCharges || 0), 0);

  return (
    <div className="flex flex-col h-full bg-white">
      <div className="flex bg-white border-b sticky top-0 z-40 px-2 overflow-x-auto no-scrollbar">
         {[
           { id: 'stats', label: 'Monitor', icon: <TrendingUp size={14}/> },
           { id: 'riders', label: 'Captains', icon: <Users size={14}/> },
           { id: 'rides', label: 'History', icon: <FileText size={14}/> }
         ].map(tab => (
           <button 
             key={tab.id} onClick={() => setActiveTab(tab.id as any)}
             className={`flex-1 py-4 font-black uppercase text-[9px] tracking-widest border-b-4 transition-all flex items-center justify-center gap-2 ${activeTab === tab.id ? 'border-[#FFDD00] text-black' : 'border-transparent text-gray-300'}`}
           >
             {tab.icon} {tab.label}
           </button>
         ))}
      </div>

      <div className="p-6 space-y-6 pb-24 flex-1 overflow-y-auto">
        {activeTab === 'stats' && (
          <div className="space-y-6">
            <div className="bg-white p-8 rounded-[2.5rem] shadow-xl border border-gray-100">
               <p className="text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Total Gross Volume</p>
               <p className="text-5xl font-black italic tracking-tighter">{formatCurrency(totalCollected)}</p>
            </div>
            <div className="grid grid-cols-2 gap-4">
               <div className="bg-black text-[#FFDD00] p-6 rounded-[2rem]">
                  <p className="text-[8px] font-black uppercase opacity-60">Admin Revenue</p>
                  <p className="text-2xl font-black italic">{formatCurrency(totalCommission + totalWaiting)}</p>
               </div>
               <div className="bg-white p-6 rounded-[2rem] border-2 border-gray-50 shadow-sm">
                  <p className="text-[8px] font-black uppercase text-gray-400">Waiting Fees</p>
                  <p className="text-2xl font-black italic text-green-600">{formatCurrency(totalWaiting)}</p>
               </div>
            </div>
            <div className="bg-indigo-50 p-6 rounded-[2rem] border-2 border-indigo-100 flex items-center gap-4">
              <Wallet className="text-indigo-600" />
              <div>
                <p className="text-[8px] font-black uppercase text-indigo-400">Total Unpaid Dues</p>
                <p className="text-xl font-black italic text-indigo-700">{formatCurrency(riders.reduce((a, b) => a + (b.commissionOwed || 0), 0))}</p>
              </div>
            </div>
          </div>
        )}

        {activeTab === 'riders' && (
          <div className="space-y-4">
            {riders.map(rider => (
              <div key={rider.id} className="bg-white p-6 rounded-[2rem] shadow-sm border border-gray-100 space-y-4">
                 <div className="flex justify-between items-center">
                    <div>
                      <h4 className="font-black text-lg uppercase italic">{rider.name}</h4>
                      <p className="text-[10px] font-black text-gray-400 tracking-widest uppercase">{rider.bikeNumber}</p>
                    </div>
                    <span className={`px-4 py-1 rounded-full text-[8px] font-black uppercase ${rider.riderStatus === 'Active' ? 'bg-green-100 text-green-600' : 'bg-red-100 text-red-600'}`}>
                      {rider.riderStatus}
                    </span>
                 </div>
                 <div className="grid grid-cols-2 gap-2">
                    <div className="bg-gray-50 p-4 rounded-2xl">
                       <p className="text-[8px] font-black uppercase text-gray-400">Commission Due</p>
                       <p className={`font-black ${rider.commissionOwed && rider.commissionOwed > 100 ? 'text-red-500' : 'text-black'}`}>{formatCurrency(rider.commissionOwed || 0)}</p>
                    </div>
                    <button onClick={() => onMarkPaid(rider.id)} className="bg-black text-[#FFDD00] rounded-2xl font-black uppercase text-[10px]">Mark Paid</button>
                 </div>
                 <div className="flex gap-2">
                    <button onClick={() => onUpdateStatus(rider.id, 'Active')} className="flex-1 bg-green-500 text-white py-3 rounded-2xl font-black uppercase text-[9px]">Activate</button>
                    <button onClick={() => onUpdateStatus(rider.id, 'Suspended')} className="flex-1 bg-red-500 text-white py-3 rounded-2xl font-black uppercase text-[9px]">Suspend</button>
                 </div>
              </div>
            ))}
          </div>
        )}

        {activeTab === 'rides' && (
          <div className="space-y-4">
            {rides.sort((a,b) => b.createdAt - a.createdAt).map(ride => (
              <div key={ride.id} className="bg-white p-6 rounded-[2rem] border border-gray-100 space-y-3">
                 <div className="flex justify-between items-start">
                    <div>
                       <p className="text-[8px] font-black uppercase text-gray-400">{new Date(ride.createdAt).toLocaleString()}</p>
                       <p className="font-black uppercase text-xs italic">{ride.customerPhone} → {ride.riderName || 'Searching'}</p>
                    </div>
                    <p className={`text-xs font-black uppercase px-2 py-1 rounded ${ride.status === 'COMPLETED' ? 'text-green-600' : 'text-gray-400'}`}>{ride.status}</p>
                 </div>
                 {ride.status === 'COMPLETED' && (
                   <div className="grid grid-cols-3 gap-2 border-t pt-3">
                      <div className="text-center"><p className="text-[7px] font-black uppercase text-gray-400">Collected</p><p className="text-xs font-black">{formatCurrency(ride.actualFareCollected || 0)}</p></div>
                      <div className="text-center"><p className="text-[7px] font-black uppercase text-gray-400">Tip</p><p className="text-xs font-black">{formatCurrency(ride.tipAmount || 0)}</p></div>
                      <div className="text-center"><p className="text-[7px] font-black uppercase text-gray-400">Waiting</p><p className="text-xs font-black text-green-600">{formatCurrency(ride.waitingCharges || 0)}</p></div>
                   </div>
                 )}
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminView;
