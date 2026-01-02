
import React from 'react';
import { UserRole } from '../types';
import { LogOut, Bike, User, ShieldCheck } from 'lucide-react';

interface LayoutProps {
  children: React.ReactNode;
  role: UserRole;
  userName: string;
  onLogout: () => void;
}

const Layout: React.FC<LayoutProps> = ({ children, role, userName, onLogout }) => {
  const getHeaderColor = () => {
    return 'bg-[#FFDD00]';
  };

  return (
    <div className="min-h-screen flex flex-col bg-gray-50 max-w-md mx-auto shadow-2xl relative">
      {/* Header */}
      <header className={`${getHeaderColor()} text-black p-5 flex items-center justify-between sticky top-0 z-50 shadow-sm`}>
        <div className="flex items-center gap-3">
          <div className="bg-black p-1.5 rounded-lg shadow-inner">
            <Bike size={20} className="text-[#FFDD00] animate-bounce" />
          </div>
          <h1 className="text-xl font-black tracking-tighter italic uppercase">Darbhanga Bikes</h1>
        </div>
        <div className="flex items-center gap-4">
          <span className="text-[10px] bg-black text-[#FFDD00] px-3 py-1 rounded-full uppercase font-black">
            {role}
          </span>
          <button 
            onClick={(e) => {
              e.stopPropagation();
              onLogout();
            }} 
            className="p-2 hover:bg-black/10 rounded-full transition-colors active:scale-90"
          >
            <LogOut size={20} />
          </button>
        </div>
      </header>

      {/* Main Content */}
      <main className="flex-1 overflow-y-auto">
        {children}
      </main>

      {/* Profile Snippet - Changed from absolute to standard flow footer */}
      <footer className="bg-white border-t border-gray-100 p-4 flex items-center gap-4 z-50">
        <div className="w-12 h-12 rounded-full bg-[#FFDD00] flex items-center justify-center border-4 border-gray-50 shadow-sm">
          {role === 'admin' ? <ShieldCheck className="text-black" /> : <User className="text-black" />}
        </div>
        <div className="flex-1">
          <p className="text-sm font-black uppercase tracking-tight">{userName}</p>
          <p className="text-[10px] text-gray-400 font-bold uppercase tracking-widest">Profile & Activity</p>
        </div>
        <div className="h-2 w-2 rounded-full bg-green-500 animate-pulse" />
      </footer>
    </div>
  );
};

export default Layout;
