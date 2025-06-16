import React, { useState } from 'react';
import axios from 'axios';
import { useNavigation } from '@react-navigation/native'; // Importe o hook useNavigation
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Image } from 'react-native'; // Importe os componentes do React Native
import { useAuth } from '../contexts/AuthContext';

// Renomeei para LoginScreen para seguir a convenção de nomeclatura dos seus outros arquivos de tela
const LoginScreen = () => {
  const [form, setForm] = useState({ email: '', password: '', remember: false });
  const navigation = useNavigation(); // Inicialize o useNavigation
  const { login } = useAuth();

  const handleChange = (name, value) => {
    setForm(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleLogin = async () => {
    try {
      const response = await axios.post('http://localhost:8081/auth/login', {
        email: form.email,
        password: form.password
      });
      console.log('Login bem-sucedido:', response.data);

      navigation.navigate('Home');
    } catch (err) {
      console.error('Erro de login:', err);
      alert('E-mail ou senha inválidos');
    }
  };

  const handleGoogleLogin = () => {
    alert('O login com Google precisa de uma implementação nativa para React Native (ex: via Expo Auth Sessions ou bibliotecas OAuth).');
  };

  return (
    <View style={styles.container}>
      <View style={styles.formContainer}>
        <Text style={styles.title}>Informações da conta:</Text>

        <TextInput
          placeholder="E-mail ou telefone"
          value={form.email}
          onChangeText={(text) => handleChange('email', text)} // Use onChangeText para TextInput
          style={styles.input}
          keyboardType="email-address"
          autoCapitalize="none"
        />
        <View style={styles.passwordWrapper}>
          <TextInput
            placeholder="Senha"
            value={form.password}
            onChangeText={(text) => handleChange('password', text)} // Use onChangeText para TextInput
            style={{ ...styles.input, marginBottom: 0 }}
            secureTextEntry // Para ocultar a senha
          />
          <Text style={styles.eyeIcon}>👁️</Text>
        </View>

        <View style={styles.row}>
          <TouchableOpacity onPress={() => handleChange('remember', !form.remember)} style={styles.checkboxLabel}>
            <Text>{form.remember ? '☑' : '☐'} Lembrar informações</Text>
          </TouchableOpacity>
          {/* AQUI É ONDE VOCÊ FAZ A NAVEGAÇÃO PARA ForgotPasswordScreen */}
          <TouchableOpacity onPress={() => navigation.navigate('ForgotPassword')} style={styles.link}>
            <Text style={{ color: '#00a868' }}>Esqueceu a senha?</Text>
          </TouchableOpacity>
        </View>

        <TouchableOpacity onPress={handleLogin} style={styles.loginButton}>
          <Text style={styles.loginButtonText}>Entrar</Text>
        </TouchableOpacity>

        <View style={styles.dividerContainer}>
          <View style={styles.hr} />
          <Text style={styles.ou}>ou</Text>
          <View style={styles.hr} />
        </View>

        <TouchableOpacity onPress={handleGoogleLogin} style={styles.googleButton}>
          {/* Para um ícone Google, você precisará usar uma imagem local ou uma biblioteca de ícones */}
          <Text style={{ fontSize: 20 }}>G</Text>
          <Text>Entrar com Google</Text>
        </TouchableOpacity>

        <Text style={styles.footerText}>
          Ainda não tem uma conta?{' '}
          {/* AQUI ESTÁ A CORREÇÃO PRINCIPAL: Usando navigation.navigate */}
          <Text style={styles.createLink} onPress={() => navigation.navigate('Register')}>
            Criar conta
          </Text>
        </Text>
      </View>
    </View>
  );
};

// Adaptei seus estilos para a sintaxe do React Native (StyleSheet.create)
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff'
  },
  formContainer: {
    width: '90%',
    maxWidth: 360,
    padding: 20,
    display: 'flex',
    flexDirection: 'column'
  },
  title: {
    marginBottom: 10,
    fontSize: 20,
    fontWeight: 'bold',
  },
  input: {
    padding: 12,
    marginBottom: 10,
    borderRadius: 8,
    borderWidth: 1,
    borderColor: '#ccc',
    fontSize: 14
  },
  passwordWrapper: {
    position: 'relative',
    marginBottom: 10
  },
  eyeIcon: {
    position: 'absolute',
    right: 12,
    top: '50%',
    transform: [{ translateY: -10 }],
  },
  row: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    fontSize: 13,
    marginBottom: 10
  },
  checkboxLabel: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
  },
  link: {
    // A cor é aplicada diretamente no componente Text aninhado
  },
  loginButton: {
    padding: 12,
    backgroundColor: '#00a868',
    borderRadius: 8,
    marginBottom: 10
  },
  loginButtonText: {
    color: '#fff',
    fontWeight: 'bold',
    textAlign: 'center',
  },
  dividerContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: 10
  },
  hr: {
    flex: 1,
    borderTopWidth: 1,
    borderColor: '#ccc'
  },
  ou: {
    marginHorizontal: 10,
    fontSize: 13,
    color: '#999'
  },
  googleButton: {
    padding: 10,
    backgroundColor: '#fff',
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 8,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 10,
    marginBottom: 10
  },
  googleIcon: {
    width: 20,
    height: 20
  },
  footerText: {
    textAlign: 'center',
    fontSize: 14
  },
  createLink: {
    color: '#00a868',
    fontWeight: 'bold'
  }
});

export default LoginScreen;