const API = "/api/apod";

const dashboard = document.getElementById("dashboard");
const gallery = document.getElementById("gallery");
const galleryGrid = document.getElementById("galleryGrid");
const modal = document.getElementById("modal");
const dateInput = document.getElementById("dateInput");

const todayBtn = document.getElementById("todayBtn");
const galleryBtn = document.getElementById("galleryBtn");
const fetchDateBtn = document.getElementById("fetchDateBtn");
const modalClose = document.getElementById("modalClose");

function setActive(btn) {
  document.querySelectorAll(".nav-btn").forEach(b => b.classList.remove("active"));
  btn.classList.add("active");
}

function showDashboard() {
  dashboard.classList.remove("hidden");
  gallery.classList.add("hidden");
}
function showGallery() {
  dashboard.classList.add("hidden");
  gallery.classList.remove("hidden");
}

async function fetchJSON(url) {
  try {
    const res = await fetch(url);
    if (!res.ok) throw new Error(await res.text());
    return res.json();
  } catch (err) {
    alert("API Error: " + err.message);
  }
}

async function loadToday() {
  setActive(todayBtn);
  showDashboard();

  const pageLoader = document.getElementById("pageLoader");
  pageLoader.style.display = "flex";

  const data = await fetchJSON(`${API}/today`);
  if (data) fillAPOD(data);

  pageLoader.classList.add("hide");
  document.querySelector(".apod-section").classList.add("visible");
}

async function loadByDate(date) {
  if (!date) return alert("Please select a date!");

  const today = new Date().toISOString().split("T")[0];
  if (date > today) {
    alert("Future dates are not allowed!");
    dateInput.value = "";
    return;
  }

  showDashboard();
  setActive(todayBtn);
  document.getElementById("apodMedia").innerHTML = `<div class="loader"></div>`;

  const data = await fetchJSON(`${API}/date?date=${date}`);
  if (data) fillAPOD(data);

  dateInput.value = "";
}

function fillAPOD(data) {
  document.getElementById("apodTitle").textContent = data.title;
  document.getElementById("apodDate").textContent = data.date;
  document.getElementById("apodExplanation").textContent = data.explanation;
  document.getElementById("apodCopyright").textContent = data.copyright || "";

  const media = document.getElementById("apodMedia");
  media.innerHTML = data.media_type === "image"
    ? `<img src="${data.hdurl || data.url}" class="media-loaded">`
    : `<iframe src="${data.url}" frameborder="0" class="media-loaded"></iframe>`;
}

async function loadGallery() {
  showGallery();
  setActive(galleryBtn);

  galleryGrid.innerHTML = "";
  document.querySelector(".section-title").insertAdjacentHTML(
    "afterend",
    `<p id="loadingMsg" style="margin-bottom:15px; font-size:16px; color:#dfe6f8;">Loading...</p>`
  );

  const items = await fetchJSON(`${API}/recent?count=10`);
  galleryGrid.innerHTML = "";
  document.getElementById("loadingMsg")?.remove();

  items?.forEach(item => {
    const card = document.createElement("div");
    card.className = "gallery-card";
    card.innerHTML = `
      <img src="${item.url}">
      <p class="gallery-title">${item.title}</p>
      <p class="gallery-date">${item.date}</p>
    `;

    // Click → open modal (with full description!)
    card.onclick = () => openModal(item);

    galleryGrid.appendChild(card);
  });
}



function openModal(item) {
  modal.style.display = "flex";
  modal.classList.remove("hidden");

  document.getElementById("modalTitle").textContent = item.title;
  document.getElementById("modalDate").textContent = item.date;
  document.getElementById("modalExplanation").textContent = item.explanation;
  document.getElementById("modalCopyright").textContent = item.copyright || "";

  document.getElementById("modalMedia").innerHTML =
    item.media_type === "image"
      ? `<img src="${item.hdurl || item.url}">`
      : `<iframe src="${item.url}" width="100%" height="420"></iframe>`;
}


modalClose.onclick = () => {
  modal.style.display = "none";
  modal.classList.add("hidden");
};

todayBtn.onclick = loadToday;
galleryBtn.onclick = loadGallery;
fetchDateBtn.onclick = () => loadByDate(dateInput.value);

loadToday();

document.getElementById("previewClose").onclick = () => {
  const gp = document.getElementById("galleryPreview");
  gp.classList.remove("hidden");

};
