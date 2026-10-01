const datosCoche = document.getElementById("datosCoche");
const inputId = document.getElementById("idcoche");

const cocheURL = "http://localhost:8080/coches";

// LOGIN **************************************

function iniciarSesion() {
  const username = document.getElementById("username").value;

  const password = document.getElementById("password").value;

  // comprueba que los campos no estén vacios:
  if (username === "" || password === "") {
    document.getElementById("mensajeLogin").textContent =
      "Escribe usuario y contraseña";

    return;
  }

  const datos = new URLSearchParams();

  datos.append("username", username);
  datos.append("password", password);

  fetch("http://localhost:8080/login", {
    method: "POST",

    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },

    body: datos,

    // IMPORTANTE:
    // permite guardar/enviar la cookie de sesión
    credentials: "include",
  })
    .then((respuesta) => {
      if (!respuesta.ok) {
        throw new Error("Usuario o contraseña incorrectos");
      }

      return respuesta.json();
    })

    .then((data) => {
      console.log(data);

      document.getElementById("login").style.display = "none";  // esconde el contenido

      document.getElementById("app").style.display = "block"; //muestra el contenido
    })

    .catch((error) => {
      document.getElementById("mensajeLogin").textContent = error.message;
    });
}


// LOGOUT **************************************

function cerrarSesion() {
  fetch("http://localhost:8080/logout", {
    method: "POST",

    credentials: "include",
  })
    .then(() => {
      document.getElementById("app").style.display = "none";

      document.getElementById("login").style.display = "block";

      document.getElementById("password").value = "";
    })

    .catch((error) => {
      console.error(error);
    });
}


// COCHES **************************************

// Listener para pulsar Enter en campo input

inputId.addEventListener("keydown", (event) => {
  if (event.key === "Enter") {
    verUno();
  }
});

// VER TODOS **************************************

function verTodos() {
  fetch(cocheURL, {
    credentials: "include",
  })
    .then((respuesta) => {
      if (!respuesta.ok) {
        throw new Error("No autorizado");
      }

      return respuesta.json();
    })

    .then((data) => {
      console.log("todos los coches: ", data);

      datosCoche.innerHTML = "";

      data.forEach((coche) => {
        datosCoche.innerHTML += `
          <h3>${coche.marca} ${coche.modelo}</h3>
          <hr>
          ${coche.precio}€
          <br>
          <small>id: ${coche.id}</small>
          <br><br>
        `;
      });
    })

    .catch((error) => {
      console.log(error);
    });
}

// VER UNO **************************************

function verUno() {
  const id = inputId.value;

  fetch(`${cocheURL}/${id}`, {
    credentials: "include",
  })
    .then((respuesta) => {
      console.log("Status:", respuesta.status);

      if (!respuesta.ok) {
        throw new Error("No autorizado");
      }

      return respuesta.json();
    })

    .then((coche) => {
      datosCoche.innerHTML = "";

      if (coche === null) {
        datosCoche.innerHTML = `
          <h3>
            No hemos encontrado un coche
            con el ID ${id}
          </h3>
        `;

        return;
      }

      datosCoche.innerHTML = `
        <h3>
          ${coche.marca} ${coche.modelo}
        </h3>

        <hr>

        ${coche.precio}

        <br><br>
      `;
    })

    .catch((error) => {
      console.error(error);
    });
}

// CREAR COCHE **************************************

function crearCoche() {
  const marca = document.getElementById("marca").value;

  const modelo = document.getElementById("modelo").value;

  const matricula = document.getElementById("matricula").value;

  const precio = Number(document.getElementById("precio").value);

  const marcaId = Number(document.getElementById("marcaId").value);

  const nuevoCoche = {
    marca: marca,

    modelo: modelo,

    matricula: matricula,

    precio: precio,

    marcaId: {
      id: marcaId,
    },
  };

  fetch(cocheURL, {
    method: "POST",

    headers: {
      "Content-Type": "application/json",
    },

    credentials: "include",

    body: JSON.stringify(nuevoCoche),
  })
    .then((respuesta) => {
      if (!respuesta.ok) {
        throw new Error(`Error HTTP: ${respuesta.status}`);
      }

      return respuesta.json();
    })

    .then((coche) => {
      console.log("Coche creado:", coche);

      alert("coche añadido!");
    })

    .catch((error) => {
      console.error(error);
    });
}

// ACTUALIZAR **************************************

function actualizarCoche() {
  const id = document.getElementById("idcocheactualizar").value;

  const marca = document.getElementById("marcaAct").value;

  const modelo = document.getElementById("modeloAct").value;

  const matricula = document.getElementById("matriculaAct").value;

  const precio = document.getElementById("precioAct").value;

  if (id === "" || marca === "" || modelo === "" || precio === "") {
    alert("rellena todos los campos menos la matrícula");

    return;
  }

  const cocheActualizado = {
    marca: marca,

    modelo: modelo,

    matricula: matricula,

    precio: Number(precio),
  };

  fetch(`${cocheURL}/${id}`, {
    method: "PUT",

    headers: {
      "Content-Type": "application/json",
    },

    credentials: "include",

    body: JSON.stringify(cocheActualizado),
  })
    .then((respuesta) => {
      if (respuesta.status === 404) {
        datosCoche.innerHTML = `<p>No existe ningún coche con el ID ${id}</p>`;
      } else if (respuesta.status === 409) {
        datosCoche.innerHTML = `<p>Esa matrícula ya existe o falta algún dato</p>`;
      } else if (!respuesta.ok) {
        throw new Error(`Error HTTP: ${respuesta.status}`);
      } else {
        return respuesta.json();
      }
    })

    .then((coche) => {
      if (coche) {
        datosCoche.innerHTML = `
          <p>
            Coche con ID ${id} actualizado:
            ${coche.marca}
            ${coche.modelo},
            ${coche.precio}€
          </p>
        `;
      }
    })

    .catch((error) => {
      console.error(error);

      datosCoche.innerHTML = `<p>${error.message}</p>`;
    });
}

// BORRAR **************************************

function borrarCoche() {
  const id = document.getElementById("idcocheborrar").value;

  fetch(`${cocheURL}/${id}`, {
    credentials: "include",
  })
    .then((respuesta) => {
      if (!respuesta.ok) {
        throw new Error("No autorizado");
      }

      return respuesta.json();
    })

    .then((coche) => {
      if (coche === null) {
        datosCoche.innerHTML = `
          <p>
            No existe ningún coche
            con el ID ${id}
          </p>
        `;

        return;
      }

      return fetch(`${cocheURL}/${id}`, {
        method: "DELETE",

        credentials: "include",
      });
    })

    .then((respuesta) => {
      if (!respuesta) return;

      if (!respuesta.ok) {
        throw new Error("No se ha podido borrar el coche");
      }

      datosCoche.innerHTML = `
        <p>
          El coche con ID ${id}
          ha sido borrado correctamente.
        </p>
      `;
    })

    .catch((error) => {
      console.error(error);

      datosCoche.innerHTML = `<p>${error.message}</p>`;
    });
}
