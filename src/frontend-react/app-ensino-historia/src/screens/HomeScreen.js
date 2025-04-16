import React, { useEffect, useState } from "react";
import { View, Text, FlatList, TouchableOpacity, Image, StyleSheet, ActivityIndicator, Alert } from "react-native";
import { useNavigation } from "@react-navigation/native";
import Icon from "react-native-vector-icons/MaterialCommunityIcons";
import { getTemasComQuestoes } from "../services/api";
import { styles } from "../styles/homeStyles"; // Importando o arquivo de estilos

const difficultyImages = {
	Iniciante: require("../../assets/iniciante.png"),
	Veterano: require("../../assets/veterano.png"),
	Mestre: require("../../assets/mestre.png"),
};

const HomeScreen = () => {
	const navigation = useNavigation();
	const [themes, setThemes] = useState([]);
	const [loading, setLoading] = useState(true);
	const [refreshing, setRefreshing] = useState(false); // Estado para o pull-to-refresh

	const fetchThemes = async () => {
        try {
            setLoading(true);
            const data = await getTemasComQuestoes(); // Chama a função para buscar os temas
			setThemes(data);
		} catch (error) {
			Alert.alert("Erro", "Não foi possível carregar os temas.");
			console.error(error);
		} finally {
			setLoading(false);
			setRefreshing(false); // Finaliza o estado de refresh
		}
	};

	useEffect(() => {
		fetchThemes();
	}, []);

	const handleRefresh = () => {
		setRefreshing(true); // Ativa o estado de refresh
		fetchThemes(); // Recarrega os dados
	};

	const handleSelectTheme = theme => {
		navigation.navigate("Question", { theme });
	};

	const renderItem = ({ item }) => (
		<TouchableOpacity style={styles.card} onPress={() => handleSelectTheme(item)}>
			{item.imagem ? (
				<Image source={{ uri: item.imagem }} style={styles.image} />
			) : (
				<View style={[styles.image, { backgroundColor: "#ccc", justifyContent: "center", alignItems: "center" }]}>
					<Text style={{ color: "#555" }}>Sem imagem</Text>
				</View>
			)}
			<Text style={styles.title}>{item.nome}</Text>
			<Text style={styles.description}>{item.descricao}</Text>
			<View style={styles.details}>
				<View style={styles.difficultyContainer}>
					<Image source={difficultyImages[item.level]} style={styles.difficultyIcon} />
					<Text style={styles.level}>{item.level}</Text>
				</View>
				<Text style={styles.questions}>{item.nroQuestions} questões</Text>
			</View>
		</TouchableOpacity>
	);

	return (
		<View style={styles.container}>
			<View style={styles.header}>
				<Text style={styles.headerTitle}>Temas</Text>
				<View style={styles.iconContainer}>
					<Icon name="bell-outline" size={24} color="black" style={styles.icon} />
					<Icon name="cog-outline" size={24} color="black" style={styles.icon} />
				</View>
			</View>
			<Text style={styles.welcomeMessage}>Olá, [nome do usuário logado]</Text>
			<Text style={styles.subtitle}>Selecione qual tema gostaria de jogar</Text>

			{loading ? (
				<ActivityIndicator size="large" color="#000" style={{ marginTop: 30 }} />
			) : (
				<FlatList
					data={themes}
					renderItem={renderItem}
					keyExtractor={item => item.id}
					numColumns={1}
					contentContainerStyle={styles.list}
					refreshing={refreshing} // Estado de refresh
					onRefresh={handleRefresh} // Função de refresh
				/>
			)}
		</View>
	);
};

export default HomeScreen;
