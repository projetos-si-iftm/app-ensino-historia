import React, { useState, useEffect } from 'react';
import { View, Text, Image, TouchableOpacity, StyleSheet } from 'react-native';
import { useNavigation, useRoute } from '@react-navigation/native';
import Icon from 'react-native-vector-icons/MaterialCommunityIcons';

const difficultyImages = {
    'Iniciante': require('../../assets/iniciante.png'),
    'Veterano': require('../../assets/veterano.png'),
    'Mestre': require('../../assets/mestre.png'),
};

const QuestionScreen = () => {
    const navigation = useNavigation();
    const route = useRoute();
    const { theme } = route.params || {};

    const [questions, setQuestions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
    const [selectedOption, setSelectedOption] = useState(null);
    const [confirmed, setConfirmed] = useState(false);
    const [timeLeft, setTimeLeft] = useState(30);
    const [disabled, setDisabled] = useState(false);

    useEffect(() => {
        const fetchQuestions = async () => {
            try {
                const response = await fetch('http://localhost:8081/questoes');
                const data = await response.json();
                console.log('Dados de questoes:', data);

                const mappedQuestions = data.map(item => ({
                    id: item.id,
                    text: item.enunciado,
                    options: item.alternativas.map(alt => alt.texto),
                    correctAnswer: item.alternativas.findIndex(alt => alt.isCorreto),
                }));

                setQuestions(mappedQuestions);
            } catch (error) {
                console.error('Erro ao buscar as questões:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchQuestions();
    }, []);

    useEffect(() => {
        if (timeLeft > 0) {
            const timer = setTimeout(() => setTimeLeft(timeLeft - 1), 1000);
            return () => clearTimeout(timer);
        } else {
            handleNextQuestion();
        }
    }, [timeLeft]);

    const handleSelectOption = (index) => {
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
            navigation.navigate('Results');
        }
    };

    if (loading) {
        return (
            <View style={styles.container}>
                <Text style={styles.loadingText}>Carregando questões...</Text>
            </View>
        );
    }

    if (!theme || questions.length === 0) {
        return (
            <View style={styles.container}>
                <Text style={styles.errorText}>Erro ao carregar as questões. Verifique os dados fornecidos.</Text>
            </View>
        );
    }

    const question = questions[currentQuestionIndex];

    return (
        <View style={styles.container}>
            <View style={styles.header}>
                <TouchableOpacity onPress={() => navigation.goBack()} style={styles.exitButton}>
                    <Icon name="arrow-left" size={24} color="black" />
                    <Text style={styles.exitText}>Sair</Text>
                </TouchableOpacity>
                <TouchableOpacity style={styles.reportButton}>
                    <Icon name="alert-circle-outline" size={24} color="black" />
                </TouchableOpacity>
            </View>
            <View style={styles.bannerContainer}>
                <Image source={theme.image} style={styles.banner} />
                <Text style={styles.progressOverlay}>{currentQuestionIndex + 1} de {questions.length}</Text>
                <View style={styles.timerOverlay}>
                    <Text style={styles.timerText}>{timeLeft}s</Text>
                </View>
            </View>
            <View style={styles.content}>
                <View style={styles.themeInfo}>
                    <Text style={styles.themeTitle}>Tema: {theme.title}</Text>
                    <View style={styles.difficultyContainer}>
                        <Image source={difficultyImages[theme.level]} style={styles.difficultyIcon} />
                        <Text style={styles.difficultyText}>{theme.level}</Text>
                    </View>
                </View>
                <Text style={styles.questionText}>Pergunta {currentQuestionIndex + 1}:</Text>
                <Text style={styles.question}>{question.text}</Text>
                {question.options.map((option, index) => (
                    <TouchableOpacity
                        key={index}
                        style={[
                            styles.option,
                            selectedOption === index && styles.selectedOption,
                            confirmed && index === question.correctAnswer && styles.correctOption,
                            confirmed && selectedOption === index && selectedOption !== question.correctAnswer && styles.wrongOption
                        ]}
                        onPress={() => handleSelectOption(index)}
                        disabled={disabled}
                    >
                        <Icon
                            name={selectedOption === index ? "checkbox-marked" : "checkbox-blank-outline"}
                            size={20}
                            color={confirmed ? (index === question.correctAnswer ? "green" : "red") : "gray"}
                        />
                        <Text>{option}</Text>
                    </TouchableOpacity>
                ))}
                <TouchableOpacity
                    style={[styles.confirmButton, disabled && styles.disabledButton]}
                    onPress={handleConfirm}
                    disabled={disabled}
                >
                    <Text style={styles.confirmText}>Confirmar</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: 'white' },
    header: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', padding: 20 },
    progressOverlay: { position: 'absolute', top: 10, left: 10, backgroundColor: '#1DB954', color: 'white', padding: 5, borderRadius: 5 },
    correctOption: { backgroundColor: '#D4F4DD' },
    wrongOption: { backgroundColor: '#F8D7DA' },
    exitButton: { flexDirection: 'row', alignItems: 'center' },
    exitText: { fontSize: 16, marginLeft: 5 },
    reportButton: { padding: 5 },
    bannerContainer: { position: 'relative' },
    banner: { width: '100%', height: 180 },
    timerOverlay: { position: 'absolute', top: 10, right: 10, backgroundColor: 'rgba(0,0,0,0.6)', padding: 5, borderRadius: 10 },
    timerText: { fontSize: 16, color: 'white', fontWeight: 'bold' },
    content: { padding: 20 },
    themeInfo: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center' },
    difficultyContainer: { flexDirection: 'row', alignItems: 'center' },
    difficultyIcon: { width: 14, height: 16, marginRight: 5 },
    difficultyText: { fontSize: 14, color: 'gray' },
    themeTitle: { fontSize: 16, fontWeight: 'bold' },
    questionText: { fontSize: 16, fontWeight: 'bold', marginTop: 10 },
    question: { fontSize: 14, marginVertical: 10 },
    wrongQuestion: { color: 'red' },
    option: { flexDirection: 'row', alignItems: 'center', padding: 15, borderWidth: 1, borderRadius: 10, marginVertical: 5 },
    selectedOption: { backgroundColor: '#E0E0E0' },
    confirmButton: { backgroundColor: 'green', padding: 15, borderRadius: 10, marginTop: 20 },
    disabledButton: { backgroundColor: '#A5D6A7' },
    confirmText: { color: 'white', textAlign: 'center', fontWeight: 'bold' },
    loadingText: { fontSize: 16, textAlign: 'center', marginTop: 50 },
    errorText: { fontSize: 16, color: 'red', textAlign: 'center', marginTop: 50 }
});

export default QuestionScreen;
