import React from 'react';
import AppNavigator from './src/navigation/AppNavigator';
import { AuthProvider } from './src/contexts/AuthContext';


export default function App() {
  return (
    <AuthProvider> {/* <-- Envolva seu AppNavigator com o AuthProvider */}
      <AppNavigator />
    </AuthProvider>
  );
}