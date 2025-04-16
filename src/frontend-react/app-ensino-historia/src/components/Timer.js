import React from "react";
import { View, Text } from "react-native";
import { styles } from "../styles/questionsStyles";

export const Timer = React.memo(({ timeLeft }) => {
	return (
		<View style={styles.timerOverlay}>
			<Text style={styles.timerText}>{timeLeft}s</Text>
		</View>
	);
});
