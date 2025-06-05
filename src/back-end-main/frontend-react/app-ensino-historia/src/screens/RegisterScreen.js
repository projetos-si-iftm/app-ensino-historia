import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  Button,
  Alert,
  StyleSheet,
  TouchableOpacity,
  ScrollView
} from 'react-native';
import axios from 'axios';
import { useNavigation } from '@react-navigation/native';

export default function RegisterScreen() {
  const navigation = useNavigation();
  const [form, setForm] = useState({
    name: '',
    email: '',
    password: '',
    isTeacher: false,
    classId: ''
  });

  const handleChange = (name, value) => {
    setForm((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const toggleTeacher = () => {
    setForm((prev) => ({ ...prev, isTeacher: !prev.isTeacher }));
  };

  const handleRegister = async () => {
    try {
      const response = await axios.post('http://localhost:8080/autenticacao/auth/register', form);
      Alert.alert('Sucesso', 'Conta criada com sucesso!');
      navigation.replace('Login');
    } catch (error) {
      Alert.alert('Erro', 'Erro ao criar conta. Verifique os dados e tente novamente.');
    }
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <View style={styles.formContainer}>
        <Text style={styles.title}>Criar Conta</Text>

        <TextInput
          placeholder="Nome completo"
          value={form.name}
          onChangeText={(text) => handleChange('name', text)}
          style={styles.input}
        />
        <TextInput
          placeholder="E-mail"
          value={form.email}
          onChangeText={(text) => handleChange('email', text)}
          style={styles.input}
          keyboardType="email-address"
          autoCapitalize="none"
        />
        <TextInput
          placeholder="Senha"
          value={form.password}
          onChangeText={(text) => handleChange('password', text)}
          style={styles.input}
          secureTextEntry
        />

        <TextInput
          placeholder="ID da turma (opcional)"
          value={form.classId}
          onChangeText={(text) => handleChange('classId', text)}
          style={styles.input}
        />

        <TouchableOpacity onPress={toggleTeacher} style={styles.checkboxContainer}>
          <Text style={styles.checkbox}>
            {form.isTeacher ? '☑' : '☐'} Sou professor(a)
          </Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.loginButton} onPress={handleRegister}>
          <Text style={styles.loginButtonText}>Cadastrar</Text>
        </TouchableOpacity>

        <Text style={styles.footerText}>
          Já tem uma conta?{' '}
          <Text style={styles.createLink} onPress={() => navigation.replace('Login')}>
            Fazer login
          </Text>
        </Text>
      </View>
    </ScrollView>
  );
}


const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingVertical: 20, // Adicionado para um melhor espaçamento em ScrollView
  },
  formContainer: {
    width: '90%',
    maxWidth: 360,
    padding: 20,
    backgroundColor: '#fff', // Geralmente é bom ter um background para o container do formulário
    borderRadius: 10,
    shadowColor: '#000', // Adicione sombra para um visual melhor
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 5,
    elevation: 3, // Para sombra no Android
  },
  title: {
    fontSize: 24, // Ajustado para ser um pouco maior
    marginBottom: 20, // Ajustado para mais espaço
    fontWeight: 'bold',
    textAlign: 'center', // Centraliza o título
  },
  input: {
    height: 50, // Adicione altura para os inputs
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 8,
    paddingHorizontal: 15, // Padding horizontal para o texto dentro do input
    marginBottom: 15, // Mais espaço entre os inputs
    fontSize: 16,
  },
  checkboxContainer: {
    flexDirection: 'row', // Para alinhar o checkbox e o texto
    alignItems: 'center',
    marginBottom: 20, // Mais espaço após o checkbox
  },
  checkbox: {
    fontSize: 16, // Ajustado o tamanho da fonte
    marginLeft: 5, // Espaçamento entre o ícone do checkbox e o texto
  },
  loginButton: {
    backgroundColor: '#00a868',
    paddingVertical: 15, // Padding vertical para o botão
    borderRadius: 8,
    alignItems: 'center',
    marginBottom: 15,
  },
  loginButtonText: {
    color: '#fff',
    fontWeight: 'bold',
    fontSize: 18, // Ajustado o tamanho da fonte
  },
  footerText: {
    textAlign: 'center',
    fontSize: 14,
    marginTop: 10, // Espaço acima do texto do rodapé
  },
  createLink: {
    color: '#00a868',
    fontWeight: 'bold',
  }
});