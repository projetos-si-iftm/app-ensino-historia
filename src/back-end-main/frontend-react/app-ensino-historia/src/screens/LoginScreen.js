import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Alert } from 'react-native';
import Icon from 'react-native-vector-icons/MaterialCommunityIcons';
import { login } from '../services/auth';

const LoginScreen = ({ navigation }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [remember, setRemember] = useState(false);

    const handleLogin = async () => {
        try {
            const data = await login(email, password);
            Alert.alert('Sucesso', 'Login realizado!');
            console.log(data);
        } catch (error) {
            Alert.alert('Erro', 'Falha ao fazer login, verifique suas credenciais.');
        }
    };

    return (
        <View style={styles.container}>
            <Text style={styles.label}>Informações da conta:</Text>
            <TextInput
                style={styles.input}
                placeholder="E-mail ou telefone"
                value={email}
                onChangeText={setEmail}
                keyboardType="email-address"
                autoCapitalize="none"
            />
            <View style={styles.passwordContainer}>
                <TextInput
                    style={styles.inputPassword}
                    placeholder="Senha"
                    value={password}
                    onChangeText={setPassword}
                    secureTextEntry={!showPassword}
                />
                <TouchableOpacity onPress={() => setShowPassword(!showPassword)}>
                    <Icon name={showPassword ? "eye-off" : "eye"} size={24} color="gray" />
                </TouchableOpacity>
            </View>
            
            <View style={styles.rememberContainer}>
                <TouchableOpacity onPress={() => setRemember(!remember)}>
                    <Icon name={remember ? "checkbox-marked" : "checkbox-blank-outline"} size={20} color="black" />
                </TouchableOpacity>
                <Text style={styles.rememberText}>Lembrar informações</Text>
                <TouchableOpacity>
                    <Text style={styles.forgotPassword}>Esqueceu a senha?</Text>
                </TouchableOpacity>
            </View>

            <TouchableOpacity style={styles.loginButton} onPress={handleLogin}>
                <Text style={styles.loginButtonText}>Entrar</Text>
            </TouchableOpacity>
            
            <Text style={styles.orText}>ou</Text>

            <TouchableOpacity style={styles.googleButton}>
                <Icon name="google" size={20} color="white" />
                <Text style={styles.googleButtonText}>Entrar com Google</Text>
            </TouchableOpacity>

            <View style={styles.signupContainer}>
                <Text>Ainda não tem uma conta?</Text>
                <TouchableOpacity onPress={() => navigation.navigate('SignUp')}>
                    <Text style={styles.signupText}> Criar conta</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, padding: 20, justifyContent: 'center' },
    label: { fontSize: 16, marginBottom: 10 },
    input: { borderWidth: 1, borderColor: '#ccc', padding: 10, borderRadius: 5, marginBottom: 10 },
    passwordContainer: { flexDirection: 'row', alignItems: 'center', borderWidth: 1, borderColor: '#ccc', padding: 10, borderRadius: 5, marginBottom: 10 },
    inputPassword: { flex: 1 },
    rememberContainer: { flexDirection: 'row', alignItems: 'center', marginBottom: 20 },
    rememberText: { marginLeft: 5 },
    forgotPassword: { color: 'green', marginLeft: 'auto' },
    loginButton: { backgroundColor: 'green', padding: 15, borderRadius: 5, alignItems: 'center' },
    loginButtonText: { color: 'white', fontSize: 16 },
    orText: { textAlign: 'center', marginVertical: 10 },
    googleButton: { flexDirection: 'row', alignItems: 'center', justifyContent: 'center', backgroundColor: '#4285F4', padding: 15, borderRadius: 5 },
    googleButtonText: { color: 'white', marginLeft: 10, fontSize: 16 },
    signupContainer: { flexDirection: 'row', justifyContent: 'center', marginTop: 20 },
    signupText: { color: 'green', fontWeight: 'bold' },
});

export default LoginScreen;
