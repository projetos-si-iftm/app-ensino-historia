import React, { createContext, useState, useContext, useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage'; // Para persistir o login

// Crie o contexto
const AuthContext = createContext(null);

// Crie um provedor para o contexto
export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null); // Aqui armazenaremos o nome do usuário e outras infos
  const [loading, setLoading] = useState(true); // Para saber se o estado inicial já foi carregado

  // Função para fazer login
  const login = async (userData) => {
    // userData deve conter o nome do usuário e o token
    setUser(userData);
    await AsyncStorage.setItem('user', JSON.stringify(userData)); // Salva no armazenamento local
  };

  // Função para fazer logout
  const logout = async () => {
    setUser(null);
    await AsyncStorage.removeItem('user'); // Remove do armazenamento local
  };

  // Carrega as informações do usuário ao iniciar o aplicativo
  useEffect(() => {
    const loadUser = async () => {
      try {
        const storedUser = await AsyncStorage.getItem('user');
        if (storedUser) {
          setUser(JSON.parse(storedUser));
        }
      } catch (e) {
        console.error("Failed to load user from AsyncStorage", e);
      } finally {
        setLoading(false);
      }
    };
    loadUser();
  }, []);

  return (
    <AuthContext.Provider value={{ user, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
};

// Hook personalizado para usar o contexto
export const useAuth = () => {
  return useContext(AuthContext);
};