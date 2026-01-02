
import { UserProfile } from './types';

export const INITIAL_RIDER_DATA: UserProfile[] = [];

export const INITIAL_CUSTOMER_DATA: UserProfile[] = [
  {
    id: 'c1',
    name: 'Darbhanga User',
    phone: '0000000000',
    role: 'customer',
    isActive: true,
    referralCode: 'DARB001',
    joinDate: Date.now(),
    completedRidesCount: 0
  }
];

export const ADMIN_USER: UserProfile = {
  id: 'admin',
  name: 'Ayush Admin',
  phone: 'captayushverma@gmail.com',
  password: 'ayush4466',
  role: 'admin',
  isActive: true,
  referralCode: 'ADMINX',
  joinDate: Date.now(),
  completedRidesCount: 0
};
