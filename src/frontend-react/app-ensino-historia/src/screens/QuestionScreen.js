import React, { useState, useEffect, useMemo } from "react";
import { View, Text, Image, TouchableOpacity, StyleSheet } from "react-native";
import { useNavigation, useRoute } from "@react-navigation/native";
import Icon from "react-native-vector-icons/MaterialCommunityIcons";
import { styles as homeStyles } from "../styles/homeStyles"; // Importando o arquivo de estilos
import { Timer } from "../components/Timer"; // Importando o componente Timer
import { styles } from "../styles/questionsStyles";

const difficultyImages = {
	Iniciante: require("../../assets/iniciante.png"),
	Veterano: require("../../assets/veterano.png"),
	Mestre: require("../../assets/mestre.png"),
};

const QuestionScreen = () => {
	const [timeLeft, setTimeLeft] = useState(30);
	const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
	const [selectedOption, setSelectedOption] = useState(null);
	const [confirmed, setConfirmed] = useState(false);
	const [disabled, setDisabled] = useState(false);

	const route = useRoute();
	const navigation = useNavigation();

	const { theme } = route.params || {};

	const questions = useMemo(() => {
		// console.log(1, theme);
		// console.log(2, JSON.stringify(theme.questoes));
		return theme?.questoes?.length > 0 ? theme?.questoes : [];
	}, [theme]);

	if (!theme || questions.length === 0) {
		return (
			<View style={styles.container}>
				<Text style={styles.errorText}>Erro ao carregar as questões. Verifique os dados fornecidos.</Text>
			</View>
		);
	}

	useEffect(() => {
		if (timeLeft > 0) {
			const timer = setTimeout(() => setTimeLeft(timeLeft - 1), 1000);
			return () => clearTimeout(timer);
		} else {
			handleNextQuestion();
		}
	}, [timeLeft]);

	const handleSelectOption = index => {
		if (!disabled) {
			setSelectedOption(index);
		}
	};

	const handleConfirm = () => {
		if (selectedOption !== null) {
			setConfirmed(true);
			setDisabled(true);
			setTimeout(handleNextQuestion, 2000);
		}
	};

	const handleNextQuestion = () => {
		if (currentQuestionIndex < questions.length - 1) {
			setCurrentQuestionIndex(currentQuestionIndex + 1);
			setSelectedOption(null);
			setConfirmed(false);
			setDisabled(false);
			setTimeLeft(30);
		} else {
			navigation.navigate("Results");
		}
	};

	const question = questions[currentQuestionIndex];
	// console.log(3, JSON.stringify(questions));
	useEffect(() => {
		console.log(4, JSON.stringify(question));
	}, [question]);
	// console.log(5, currentQuestionIndex);

	function Cabecalho({ navigation }) {
		return (
			<View style={homeStyles.header}>
				<TouchableOpacity onPress={() => navigation.goBack()} style={styles.exitButton}>
					<Icon name="arrow-left" size={24} color="black" />
					<Text style={styles.exitText}>Exercícios</Text>
				</TouchableOpacity>
				<TouchableOpacity style={styles.reportButton}>
					<Icon name="alert-circle-outline" size={24} color="black" />
				</TouchableOpacity>
			</View>
		);
	}

	return (
		<View style={homeStyles.container}>
			<Cabecalho navigation={navigation} />
			<View style={styles.bannerContainer}>
				<Image source={theme.image} style={styles.banner} />
				<Text style={styles.progressOverlay}>
					{currentQuestionIndex + 1} de {questions.length}
				</Text>
				<Timer timeLeft={timeLeft} />
			</View>
			<View style={styles.content}>
				<View style={styles.themeInfo}>
					<Text style={styles.themeTitle}>Tema: {theme.nome}</Text>
					{/* <View style={styles.difficultyContainer}>
						<Image source={difficultyImages[theme.level]} style={styles.difficultyIcon} />
						<Text style={styles.difficultyText}>{theme.level}</Text>
					</View> */}
				</View>
				<Text style={styles.questionText}>
					Pergunta {currentQuestionIndex + 1}: {question.titulo}
				</Text>
				<Text style={styles.question}>{question.enunciado}</Text>
				{question.alternativas.map(({ texto, correto }, index) => (
					<TouchableOpacity
						key={index}
						style={[
							styles.option,
							selectedOption === index && styles.selectedOption,
							confirmed && index === correto && styles.correctOption,
							confirmed && selectedOption === index && selectedOption !== correto && styles.wrongOption,
						]}
						onPress={() => handleSelectOption(index)}
						disabled={disabled}>
						<Icon name={selectedOption === index ? "checkbox-marked" : "checkbox-blank-outline"} size={20} color={confirmed ? (index === correto ? "green" : "red") : "gray"} />
						<Text style={{marginLeft:12}}>{texto}</Text>
					</TouchableOpacity>
				))}
				<TouchableOpacity style={[styles.confirmButton, disabled && styles.disabledButton]} onPress={handleConfirm} disabled={disabled}>
					<Text style={styles.confirmText}>Confirmar</Text>
				</TouchableOpacity>
			</View>
		</View>
	);
};

export default QuestionScreen;
