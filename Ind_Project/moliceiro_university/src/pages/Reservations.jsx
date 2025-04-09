import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { parseISO, format } from "date-fns";

// Função para gerar um token aleatório
const generateUserToken = () => {
    return Math.random().toString(36).substring(2, 12); // Gera um token aleatório com 10 caracteres
};

function Reservations() {
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [userToken, setUserToken] = useState("");  // Alteramos para manter o token no estado

    // Carregar reservas e gerar o token aleatório
    useEffect(() => {
        // Gerar um novo token toda vez que a página for carregada
        setUserToken(generateUserToken());

        fetch(`http://localhost:8080/api/reservations`)
            .then((res) => {
                if (!res.ok) {
                    throw new Error("Erro ao carregar reservas");
                }
                return res.json();
            })
            .then((data) => {
                setReservations(data);
                setLoading(false);
            })
            .catch((err) => {
                console.error("Erro ao buscar reservas:", err);
                setLoading(false);
            });
    }, []);

    const cancelReservation = (mealId) => {
        fetch(`http://localhost:8080/api/reservation/meal/${mealId}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${userToken}`,  // Adicionando o token no header da requisição
            },
        })
        .then((res) => {
            if (!res.ok) {
                throw new Error("Erro ao cancelar reserva");
            }
            return res.text();  // Retorna a mensagem de sucesso como texto
        })
        .then((message) => {
            alert(message);  // Exibe a mensagem retornada como texto
    
            // Atualize o estado removendo a reserva cancelada
            setReservations((prevReservations) =>
                prevReservations.filter((reserva) => reserva.meal.id !== mealId)
            );
        })
        .catch((err) => {
            console.error("Erro ao cancelar a reserva:", err);
            alert("Erro ao cancelar a reserva.");
        });
    };

    return (
        <div>
            <header className="header">
                <h1 className="title">Minhas Reservas</h1>
                <Link to="/" className="reservas-link">Voltar</Link>
            </header>

            <main>
                {loading ? (
                    <p>Carregando reservas...</p>
                ) : reservations.length === 0 ? (
                    <p>Você ainda não tem reservas.</p>
                ) : (
                    <div className="card-grid">
                        {reservations.map((reserva) => (
                            <div key={reserva.id} className="card">
                                <h3>
                                    {reserva.meal?.createdAt
                                        ? format(parseISO(reserva.meal.createdAt), "eeee, dd MMMM yyyy", { locale: undefined })
                                        : "Data da refeição indisponível"}
                                </h3>
                                <p><strong>🍽️ Refeição:</strong> {reserva.meal?.name}</p>
                                <p><strong>📍 Restaurante:</strong> {reserva.meal?.restaurant?.name || "Não disponível"}</p>
                                <p><strong>⏱️ Reservado em:</strong> {format(parseISO(reserva.reservationDate), "dd/MM/yyyy HH:mm")}</p>

                                {/* Botão de Cancelar Reserva */}
                                <button
                                    className="cancel-btn"
                                    onClick={() => cancelReservation(reserva.meal.id)}  // Passando o mealId
                                >
                                    Cancelar Reserva
                                </button>

                            </div>
                        ))}
                    </div>
                )}
            </main>
        </div>
    );
}

export default Reservations;
