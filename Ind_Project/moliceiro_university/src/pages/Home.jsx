import { useState, useEffect } from "react";
import { parseISO, format } from "date-fns";
import { Link } from "react-router-dom";
import Banner from "../components/Banner";

function Home() {
  const [restaurants, setRestaurants] = useState([]);
  const [selectedRestaurant, setSelectedRestaurant] = useState(null);
  const [meals, setMeals] = useState([]);
  const [weatherData, setWeatherData] = useState([]);
  const [userToken] = useState("user12");
  const [showModal, setShowModal] = useState(false);
  const [mealToReserve, setMealToReserve] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/restaurants")
      .then((res) => res.json())
      .then((data) => {
        setRestaurants(data);
        if (data.length > 0) {
          setSelectedRestaurant(data[0].id);
        }
      })
      .catch((err) => console.error("Erro ao buscar restaurantes", err));
  }, []);

  useEffect(() => {
    if (selectedRestaurant) {
      fetch(`http://localhost:8080/api/meals/restaurant/${selectedRestaurant}`)
        .then((res) => res.json())
        .then((data) => {
          setMeals(data);
        })
        .catch((err) => console.error("Erro ao buscar refeições", err));
    }
  }, [selectedRestaurant]);

  useEffect(() => {
    fetch("http://localhost:8080/api/weather")
      .then((res) => res.json())
      .then((data) => {
        console.log("Resposta da API de clima:", data); // Verifique a resposta aqui
        if (data.days && data.days.length > 0) {
          setWeatherData(data.days); // Armazena as previsões de clima
        } else {
          console.log("Nenhuma previsão de clima encontrada.");
        }
      })
      .catch((err) => {
        console.error("Erro ao buscar dados de tempo", err);
      });
  }, []);
  

  const openReservationModal = (meal) => {
    setMealToReserve(meal);
    setShowModal(true);
  };

  const handleReserveConfirm = () => {
    if (!mealToReserve) return;

    const reservation = {
      userToken,
      meal: { id: mealToReserve.id },
      restaurant: { id: selectedRestaurant },
    };

    fetch("http://localhost:8080/api/reserve", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(reservation),
    })
      .then((res) => {
        if (!res.ok) {
          return res.text().then((text) => {
            throw new Error(text);
          });
        }
        return res.json();
      })
      .then((data) => {
        console.log("Reserva criada com sucesso:", data);
        alert("Reserva confirmada!");
        setShowModal(false);
        setMealToReserve(null);
      })
      .catch((err) => {
        console.error("Erro ao criar reserva:", err);
        alert("Erro ao criar reserva: " + err.message);
        setShowModal(false);
        setMealToReserve(null);
      });
  };

  const getWeatherForDate = (dateString) => {
    // Converte a data da refeição para formato "yyyy-MM-dd"
    const dateFormatted = format(parseISO(dateString), "yyyy-MM-dd");
    console.log("Data da refeição:", dateString, "Data formatada:", dateFormatted);
  
    // Buscar a previsão do tempo para a data, verificando a correspondência exata
    const weather = weatherData.find((d) => d.datetime === dateFormatted);
    console.log(weatherData); // Verifique se está acessando 'days' corretamente

    if (weather) {
      console.log("Clima encontrado para a data:", dateFormatted, weather);
    } else {
      console.log("Sem clima para a data:", dateFormatted);
    }
    return weather;
  };

  const restaurantName = restaurants.find((r) => r.id === selectedRestaurant)?.name;

  return (
    <>
      <header className="header">
        
        <h1 className="title">Moliceiro University</h1>
        <Link to="/worker">
            <button className="reservas-link"> Worker</button>
          </Link>
        <select
          className="restaurant-select"
          value={selectedRestaurant || ""}
          onChange={(e) => setSelectedRestaurant(Number(e.target.value))}
        >
          {restaurants.map((r) => (
            <option key={r.id} value={r.id}>
              {r.name}
            </option>
          ))}
        </select>
         
        <Link to="/reservas" className="reservas-link">Ver Reservas</Link>
      </header>

      <Banner />

      <main>
        <h2 className="subtitle">
          Próximas refeições em {restaurantName || "Carregando..."}
        </h2>
        <div className="card-grid">
          {meals.length > 0 ? (
            meals.map((meal) => {
              const weather = meal.createdAt ? getWeatherForDate(meal.createdAt) : null;
              return (
                <div key={meal.id} className="card">
                  <h3>
                    {meal.createdAt
                      ? format(parseISO(meal.createdAt), "eeee, dd MMMM yyyy")
                      : "Data inválida"}
                  </h3>
                  <p className="meal">
                    🍽️ {meal.name} - {meal.description}
                  </p>
                  <p className="weather">
                    {weather
                      ? `🌡️ ${weather.temp}°F | 💧 ${weather.humidity}%`
                      : "🌤️ Sem previsão disponível"}
                  </p>
                  <button
                    className="reserve-btn"
                    onClick={() => openReservationModal(meal)}
                  >
                    Reservar
                  </button>
                </div>
              );
            })
          ) : (
            <p>Carregando as refeições...</p>
          )}
        </div>

      
      </main>

      {showModal && mealToReserve && (
        <div className="modal-overlay">
          <div className="modal">
            <h3>Confirmar reserva</h3>
            <p>
              Deseja reservar a refeição: <strong>{mealToReserve.name}</strong>?
            </p>
            <div className="modal-buttons">
              <button onClick={handleReserveConfirm} className="confirm-btn">
                Confirmar
              </button>
              <button onClick={() => setShowModal(false)} className="cancel-btn">
                Cancelar
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}

export default Home;
