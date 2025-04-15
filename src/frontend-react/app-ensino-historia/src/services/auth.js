import axios from 'axios';

const API_URL = 'http://localhost:8080/auth';

export const login = async (email, password) => {
    try {
        const response = await axios.post(`${API_URL}/login`, { email, password });
        return response.data; // Retorna o token ou dados do usuário
    } catch (error) {
        throw error;
    }
};
