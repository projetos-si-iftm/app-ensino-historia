const TEMAS_API_URL = "https://fidiquem-questoes-api-043ed1794ea9.herokuapp.com/api/v1/temas";
const QUESTIONS_API_URL = "https://fidiquem-questoes-api-043ed1794ea9.herokuapp.com/api/v1/questoes";

export const getTemas = async () => {
	try {
		const response = await fetch(TEMAS_API_URL);
		return response.json();
	} catch (error) {
		throw error;
	}
};

export const getQuestoesByTema = async temaId => {
	try {
		const response = await fetch(`${QUESTIONS_API_URL}/tema/${temaId}`);
		if (!response.ok) {
			throw new Error("Erro ao buscar questões");
		}
		return response.json();
	} catch (error) {
		throw error;
	}
};

export const getTemasComQuestoes = async () => {
	try {
		const temas = await getTemas();
		const temasComQuestoes = await Promise.all(
			temas.map(async tema => {
				const questoes = await getQuestoesByTema(tema.id);
				const nroQuestions = questoes.length;
				return { ...tema, nroQuestions, questoes };
			})
		);
		return temasComQuestoes;
	} catch (error) {
		throw error;
	}
}
