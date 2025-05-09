import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
	container: { flex: 1, backgroundColor: "white" },
	header: { flexDirection: "row", justifyContent: "space-between", alignItems: "center", padding: 20 },
	progressOverlay: { position: "absolute", top: 10, left: 10, backgroundColor: "#1DB954", color: "white", padding: 5, borderRadius: 5 },
	correctOption: { backgroundColor: "#D4F4DD" },
	wrongOption: { backgroundColor: "#F8D7DA" },
	exitButton: { flexDirection: "row", alignItems: "center" },
	exitText: { fontSize: 16, marginLeft: 5 },
	reportButton: { padding: 5 },
	bannerContainer: { position: "relative", borderWidth: 1 },
	banner: { width: "100%", height: 180 },
	timerOverlay: { position: "absolute", top: 10, right: 10, backgroundColor: "rgba(0,0,0,0.6)", padding: 5, borderRadius: 10 },
	timerText: { fontSize: 16, color: "white", fontWeight: "bold" },
	content: { padding: 4 },
	themeInfo: { flexDirection: "row", justifyContent: "space-between", alignItems: "center" },
	difficultyContainer: { flexDirection: "row", alignItems: "center" },
	difficultyIcon: { width: 14, height: 16, marginRight: 5 },
	difficultyText: { fontSize: 14, color: "gray" },
	themeTitle: { fontSize: 16, fontWeight: "600" },
	questionText: { fontSize: 16, fontWeight: "bold", marginTop: 10 },
	question: { fontSize: 14, marginVertical: 10 },
	wrongQuestion: { color: "red" },
	option: { flexDirection: "row", alignItems: "center", padding: 16, borderWidth: 1, borderRadius: 10, marginVertical: 5 },
	selectedOption: { backgroundColor: "#E0E0E0" },
	confirmButton: { backgroundColor: "green", padding: 15, borderRadius: 10, marginTop: 20 },
	disabledButton: { backgroundColor: "#A5D6A7" },
	confirmText: { color: "white", textAlign: "center", fontWeight: "bold" },
});
