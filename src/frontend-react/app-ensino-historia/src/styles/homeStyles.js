import { StyleSheet } from 'react-native';


export const styles = StyleSheet.create({
	container: {
		flex: 1,
		marginTop: 20,
		padding: 8,
		backgroundColor: "white",
	},
	header: {
		flexDirection: "row",
		alignItems: "center",
		justifyContent: "space-between",
		paddingVertical: 10,
		borderBottomWidth: 1,
		borderBottomColor: "#E0E0E0",
	},
	headerTitle: {
		fontSize: 18,
		fontWeight: "bold",
		textAlign: "left",
		flex: 1,
	},
	iconContainer: {
		flexDirection: "row",
		alignItems: "center",
		justifyContent: "flex-end",
	},
	icon: {
		marginLeft: 15,
	},
	welcomeMessage: {
		fontSize: 16,
		fontWeight: "600",
		color: "#333",
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
		backgroundColor: "#CAF6CA",
		borderRadius: 10,
		overflow: "hidden",
		padding: 14,
	},
	image: {
		width: "100%",
		height: 150,
		borderRadius: 10,
	},
	title: {
		fontSize: 16,
		fontWeight: "bold",
		marginTop: 5,
	},
	description: {
		fontSize: 14,
		color: "#666",
		marginTop: 2,
	},
	details: {
		flexDirection: "row",
		justifyContent: "space-between",
		alignItems: "center",
		marginTop: 5,
	},
	difficultyContainer: {
		flexDirection: "row",
		alignItems: "center",
	},
	difficultyIcon: {
		width: 14,
		height: 16,
		marginRight: 5,
	},
	level: {
		fontSize: 14,
		color: "gray",
	},
	questions: {
		color: "gray",
	},
});
