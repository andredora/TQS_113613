import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function WorkerHome() {
  const [restaurants, setRestaurants] = useState([]);
  const [selectedRestaurant, setSelectedRestaurant] = useState(null);
  const [newMeal, setNewMeal] = useState({
    name: "",
    description: "",
    price: "",
    createdAt: "",
    restaurantName: "", 
  });

  useEffect(() => {
    fetch("http://localhost:8080/api/restaurants")
      .then((res) => res.json())
      .then((data) => {
        setRestaurants(data);
        if (data.length > 0) {
          setSelectedRestaurant(data[0].id);
        }
      })
      .catch((err) => console.error("Erro ao carregar restaurantes:", err));
  }, []);

  useEffect(() => {
    if (selectedRestaurant) {
      const restaurant = restaurants.find(
        (r) => r.id === selectedRestaurant
      );
      setNewMeal((prevMeal) => ({
        ...prevMeal,
        restaurantName: restaurant ? restaurant.name : "",
      }));
    }
  }, [selectedRestaurant, restaurants]);

  const handleAddMeal = () => {
    const meal = {
      name: newMeal.name,
      description: newMeal.description,
      price: parseFloat(newMeal.price),
      createdAt: newMeal.createdAt + "T00:00:00", 
      restaurantName: newMeal.restaurantName, 
    };

    fetch("http://localhost:8080/api/add_meal", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(meal),
    })
      .then((res) => {
        if (!res.ok) throw new Error("Erro ao criar refeição");
        return res.json();
      })
      .then((data) => {
        alert("Refeição adicionada com sucesso!");
        setNewMeal({ name: "", description: "", price: "", createdAt: "", restaurantName: "" });
      })
      .catch((err) => console.error("Erro ao adicionar ementa:", err));
  };

  return (
    <div className="app">
      <header className="header">
        <h1 className="title">Painel do Funcionário</h1>
        <Link to="/">
                    <button className="reservas-link"> User</button>
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
      </header>

      <main>
        <h2 className="subtitle">Adicionar nova refeição</h2>
        <input
          type="text"
          placeholder="Nome"
          value={newMeal.name}
          onChange={(e) => setNewMeal({ ...newMeal, name: e.target.value })}
        />
        <input
          type="text"
          placeholder="Descrição"
          value={newMeal.description}
          onChange={(e) =>
            setNewMeal({ ...newMeal, description: e.target.value })
          }
        />
        <input
          type="number"
          step="0.01"
          placeholder="Preço (€)"
          value={newMeal.price}
          onChange={(e) => setNewMeal({ ...newMeal, price: e.target.value })}
        />
        <input
          type="date"
          value={newMeal.createdAt}
          onChange={(e) => setNewMeal({ ...newMeal, createdAt: e.target.value })}
        />
        <button className="reserve-btn" onClick={handleAddMeal}>
          Adicionar
        </button>
      </main>
    </div>
  );
}

export default WorkerHome;
