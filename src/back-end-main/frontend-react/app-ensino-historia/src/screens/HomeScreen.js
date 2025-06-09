import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, TouchableOpacity, Image, StyleSheet, Button } from 'react-native'; // Importe Button para o botão Sair
import { useNavigation } from '@react-navigation/native';
import Icon from 'react-native-vector-icons/MaterialCommunityIcons';
import { useAuth } from '../contexts/AuthContext'; // <--- Adicionado: Importe o hook useAuth

const difficultyImages = {
    'Iniciante': require('../../assets/iniciante.png'),
    'Veterano': require('../../assets/veterano.png'),
    'Mestre': require('../../assets/mestre.png'),
};

const HomeScreen = () => {
    const [themes, setThemes] = useState([]);
    const navigation = useNavigation();
    const { user, logout } = useAuth(); // <--- Adicionado: Obtenha o objeto 'user' e a função 'logout' do contexto

    useEffect(() => {
        const fetchThemes = async () => {
            try {
                const response = await fetch('http://localhost:8082/temas');
                console.log('Response:', response);
                const data = await response.json();
                console.log('Dados recebidos da API:', data);
                // Mapear os dados recebidos para o formato esperado
                const mappedThemes = data.map(item => ({
                    id: item.id,
                    title: item.descricao,
                    //level: 'Iniciante', // Ajuste conforme necessário - Você pode buscar isso da API também
                    //questions: 10, // Ajuste conforme necessário - Você pode buscar isso da API também
                    image: { uri: `http://192.168.1.10:8082/uploads/${item.dados}` },
                }));

                setThemes(mappedThemes);
            } catch (error) {
                console.error('Erro ao buscar os temas:', error);
            }
        };
        fetchThemes();
    }, []);

    const handleSelectTheme = (theme) => {
        navigation.navigate('Question', { theme });
    };

    const renderItem = ({ item }) => (
        <TouchableOpacity style={styles.card} onPress={() => handleSelectTheme(item)}>
            <Image source={item.image} style={styles.image} />
            <Text style={styles.title}>{item.title}</Text>
            <View style={styles.details}>
                <View style={styles.difficultyContainer}>
                    {/* Exemplo de como você usaria 'level' se a API o fornecesse */}
                    {/* {item.level && <Image source={difficultyImages[item.level]} style={styles.difficultyIcon} />} */}
                    {/* {item.level && <Text style={styles.level}>{item.level}</Text>} */}
                </View>
                {/* Exemplo de como você usaria 'questions' se a API o fornecesse */}
                {/* {item.questions && <Text style={styles.questions}>{item.questions} questões</Text>} */}
            </View>
        </TouchableOpacity>
    );

    return (
        <View style={styles.container}>
            <View style={styles.header}>
                <Text style={styles.headerTitle}>Início</Text>
                <View style={styles.iconContainer}>
                    <Icon name="bell-outline" size={24} color="black" style={styles.icon} />
                    <Icon name="cog-outline" size={24} color="black" style={styles.icon} />
                </View>
            </View>

            {/* <--- Modificado: Mensagem de boas-vindas com o nome do usuário logado */}
            {user && user.name ? (
                <Text style={styles.welcomeMessage}>Olá, {user.name}!</Text>
            ) : (
                <Text style={styles.welcomeMessage}>Olá!</Text>
            )}

            <Text style={styles.subtitle}>Selecione qual tema gostaria de jogar:</Text>

            <FlatList
                data={themes}
                renderItem={renderItem}
                keyExtractor={item => item.id}
                numColumns={1}
                contentContainerStyle={styles.list}
            />

            {/* <--- Adicionado: Botão "Sair" */}
            <View style={{ marginTop: 20, width: '100%', alignItems: 'center' }}>
                <Button
                    title="Sair"
                    onPress={logout} // Chama a função de logout do contexto
                    color="#FF6347" // Cor para o botão de sair
                />
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 15,
        backgroundColor: 'white',
    },
    header: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        paddingHorizontal: 15,
        paddingVertical: 10,
        backgroundColor: 'white',
        borderBottomWidth: 1,
        borderBottomColor: '#E0E0E0',
    },
    headerTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        textAlign: 'left',
        flex: 1,
    },
    iconContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'flex-end',
    },
    icon: {
        marginLeft: 15,
    },
    welcomeMessage: { // <--- Estilos adaptados para a mensagem de boas-vindas
        fontSize: 20, // Um pouco maior para destaque
        fontWeight: 'bold',
        color: '#333',
        marginVertical: 10,
        textAlign: 'left', // Alinha à esquerda como o resto do texto
    },
    subtitle: {
        fontSize: 16,
        marginVertical: 10,
    },
    list: {
        paddingBottom: 20,
    },
    card: {
        marginBottom: 15,
        backgroundColor: '#F1F1F1',
        borderRadius: 10,
        overflow: 'hidden',
        padding: 5,
    },
    image: {
        width: '100%',
        height: 150,
        borderRadius: 10,
    },
    title: {
        fontSize: 16,
        fontWeight: 'bold',
        marginTop: 5,
    },
    details: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginTop: 5,
    },
    difficultyContainer: {
        flexDirection: 'row',
        alignItems: 'center',
    },
    difficultyIcon: {
        width: 14,
        height: 16,
        marginRight: 5,
    },
    level: {
        fontSize: 14,
        color: 'gray',
    },
    questions: {
        color: 'gray',
    },
});

export default HomeScreen;