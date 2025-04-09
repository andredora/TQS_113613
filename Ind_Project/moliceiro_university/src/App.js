import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Reservations from "./pages/Reservations";
import WorkerHome from "./pages/WorkerHome";

import "./App.css";

function App() {
  return (
    <Router>
      <div className="app">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/reservas" element={<Reservations />} />
          <Route path="/worker" element={<WorkerHome />} />

        </Routes>
      </div>
    </Router>
  );
}

export default App;
