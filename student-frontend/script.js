// ✅ Correct API URL for Spring Boot backend
const API_BASE_URL = "http://localhost:8080/api";

// Form elements
const ktuForm = document.getElementById("ktu-form");
const detailsForm = document.getElementById("details-form");

// Step elements
const ktuStep = document.getElementById("ktu-step");
const detailsStep = document.getElementById("details-step");
const successStep = document.getElementById("success-step");

// Step 1: Validate KTU ID
ktuForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const ktuId = document.getElementById("ktuId").value;

  try {
    const response = await fetch(`${API_BASE_URL}/validate-ktu`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ ktuId }),
    });

    if (response.ok) {
      const student = await response.json();

      // ✅ Fill student data
      document.getElementById("ktuIdDetails").value = student.ktuId;
      document.getElementById("name").value =
        student.firstname + " " + student.lastname;
      document.getElementById("branch").value = student.branch;
      document.getElementById("batch").value = student.batch;

      // Show next step
      ktuStep.classList.add("hidden");
      detailsStep.classList.remove("hidden");
    } else {
      alert("Invalid KTU ID. Please try again.");
    }
  } catch (error) {
    console.error("Error:", error);
    alert("Failed to connect to server.");
  }
});

// Step 2: Register student
detailsForm.addEventListener("submit", async (e) => {
  e.preventDefault();

  const formData = {
    ktuId: document.getElementById("ktuIdDetails").value,
    firstname: document.getElementById("name").value.split(" ")[0],
    lastname: document.getElementById("name").value.split(" ")[1] || "",
    branch: document.getElementById("branch").value,
    batch: document.getElementById("batch").value,
    email: document.getElementById("email").value,
    phone: document.getElementById("phone").value,
    password: document.getElementById("password").value,
  };

  try {
    const response = await fetch(`${API_BASE_URL}/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(formData),
    });

    if (response.ok) {
      detailsStep.classList.add("hidden");
      successStep.classList.remove("hidden");
    } else {
      alert("Registration failed.");
    }
  } catch (error) {
    console.error("Error:", error);
    alert("Failed to connect to server.");
  }
});
