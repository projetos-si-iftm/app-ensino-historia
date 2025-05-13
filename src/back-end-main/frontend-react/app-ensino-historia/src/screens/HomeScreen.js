import React, { useState, useEffect } from 'react'; 
import { View, Text, FlatList, TouchableOpacity, Image, StyleSheet } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import Icon from 'react-native-vector-icons/MaterialCommunityIcons';

// const themes = [
//     { id: '1', title: 'Independência do Brasil', level: 'Mestre', questions: 10, image: require('../../assets/independencia.png') },
//     { id: '2', title: 'I Reinado no Brasil', level: 'Iniciante', questions: 10, image: require('../../assets/reinado.png') },
//     { id: '3', title: 'Período Regencial', level: 'Veterano', questions: 10, image: require('../../assets/regencial.png') },
//     { id: '4', title: 'II Reinado no Brasil', level: 'Mestre', questions: 10, image: require('../../assets/reinado2.png') },
//     { id: '5', title: 'Brasil I República', level: 'Iniciante', questions: 10, image: require('../../assets/republica.png') },
//     { id: '6', title: 'Imperialismo século XIX', level: 'Veterano', questions: 10, image: require('../../assets/imperialismo.png') },
// ];

const difficultyImages = {
    'Iniciante': require('../../assets/iniciante.png'),
    'Veterano': require('../../assets/veterano.png'),
    'Mestre': require('../../assets/mestre.png'),
};

const HomeScreen = () => {
    const [themes, setThemes] = useState([]);
    const navigation = useNavigation();

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
                    //level: 'Iniciante', // Ajuste conforme necessário
                    //questions: 10, // Ajuste conforme necessário
                    //image: { uri: `http://localhost:8082/uploads/${item.dados}` }, // URL da imagem
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
                    <Image source={difficultyImages[item.level]} style={styles.difficultyIcon} />
                    <Text style={styles.level}>{item.level}</Text>
                </View>
                <Text style={styles.questions}>{item.questions} questões</Text>
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
            <Text style={styles.welcomeMessage}>Olá, Olavo!</Text>
            <Text style={styles.subtitle}>Selecione qual tema gostaria de jogar:</Text>
            <FlatList
                data={themes}
                renderItem={renderItem}
                keyExtractor={item => item.id}
                numColumns={1}
                contentContainerStyle={styles.list}
            />
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
        flex: 1, // Permite que o texto ocupe o espaço necessário
    },
    iconContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'flex-end', // Alinha os ícones à direita
    },
    icon: {
        marginLeft: 15, // Espaçamento entre os dois ícones
    },
    welcomeMessage: {
        fontSize: 16,
        fontWeight: '600',
        color: '#333',
        marginVertical: 10,
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

