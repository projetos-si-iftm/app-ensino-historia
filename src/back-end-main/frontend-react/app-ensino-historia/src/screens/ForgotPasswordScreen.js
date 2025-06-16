import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Alert,
  ActivityIndicator // Para indicar carregamento
} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import axios from 'axios'; // Para fazer requisições HTTP

export default function ForgotPasswordScreen() {
  const navigation = useNavigation();
  const [email, setEmail] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSendResetEmail = async () => {
    if (!email) {
      Alert.alert('Erro', 'Por favor, insira seu e-mail.');
      return;
    }

    setLoading(true);
    try {
      // Endpoint no seu backend para solicitar a redefinição de senha
      // Você precisará configurar este endpoint no seu Spring Boot
      const response = await axios.post('http://localhost:8083/autenticacao/auth/forgot-password', { email });
      // Ou talvez http://localhost:8080/autenticacao/auth/forgot-password dependendo da sua porta de auth

      Alert.alert(
        'E-mail Enviado',
        'Se o e-mail estiver registrado, você receberá um link para redefinir sua senha.'
      );
      navigation.goBack(); // Volta para a tela de login após o envio
    } catch (error) {
      console.error('Erro ao enviar e-mail de redefinição:', error);
      Alert.alert(
        'Erro',
        'Não foi possível enviar o e-mail de redefinição. Verifique seu e-mail e tente novamente.'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.formContainer}>
        <Text style={styles.title}>Esqueceu a Senha?</Text>
        <Text style={styles.subtitle}>
          Digite seu e-mail abaixo e enviaremos um link para redefinir sua senha.
        </Text>

        <TextInput
          placeholder="Seu e-mail"
          value={email}
          onChangeText={setEmail}
          style={styles.input}
          keyboardType="email-address"
          autoCapitalize="none"
        />

        <TouchableOpacity
          style={styles.sendButton}
          onPress={handleSendResetEmail}
          disabled={loading} // Desabilita o botão enquanto carrega
        >
          {loading ? (
            <ActivityIndicator color="#fff" />
          ) : (
            <Text style={styles.sendButtonText}>Enviar Link de Redefinição</Text>
          )}
        </TouchableOpacity>

        <TouchableOpacity onPress={() => navigation.goBack()} style={styles.backToLogin}>
          <Text style={styles.backToLoginText}>Voltar para o Login</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f0f2f5',
  },
  formContainer: {
    width: '90%',
    maxWidth: 400,
    padding: 25,
    backgroundColor: '#fff',
    borderRadius: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.1,
    shadowRadius: 10,
    elevation: 8,
  },
  title: {
    fontSize: 26,
    fontWeight: 'bold',
    marginBottom: 15,
    textAlign: 'center',
    color: '#333',
  },
  subtitle: {
    fontSize: 15,
    marginBottom: 25,
    textAlign: 'center',
    color: '#666',
  },
  input: {
    height: 50,
    borderColor: '#ddd',
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 15,
    marginBottom: 20,
    fontSize: 16,
    backgroundColor: '#f9f9f9',
  },
  sendButton: {
    backgroundColor: '#00a868',
    paddingVertical: 15,
    borderRadius: 8,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 15,
  },
  sendButtonText: {
    color: '#fff',
    fontSize: 18,
    fontWeight: 'bold',
  },
  backToLogin: {
    paddingVertical: 10,
    alignItems: 'center',
  },
  backToLoginText: {
    color: '#00a868',
    fontSize: 16,
    fontWeight: '600',
  },
});