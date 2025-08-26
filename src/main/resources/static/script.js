const API_BASE_URL = 'http://localhost:8080';

    // Funkcja do wyświetlania komunikatów
    function showMessage(message, type = 'success') {
        const messageDiv = document.getElementById('message');
        messageDiv.innerHTML = `<div class="${type}">${message}</div>`;
        setTimeout(() => {
            messageDiv.innerHTML = '';
        }, 3000);
    }

    // Funkcja do dodawania produktu
    async function addProduct() {
        const name = document.getElementById('productName').value.trim();
        const quantity = parseInt(document.getElementById('productQuantity').value);

        // Walidacja
        if (!name || !quantity) {
            showMessage('Wszystkie pola są wymagane!', 'error');
            return;
        }

        const product = {
            name: name,
            quantity: quantity
        };

        try {
            const response = await fetch(`${API_BASE_URL}/add`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(product)
            });

            if (response.ok) {
                showMessage('Produkt został dodany pomyślnie!', 'success');
                // Wyczyść formularz
                document.getElementById('productName').value = '';
                document.getElementById('productQuantity').value = '';

                // Odśwież listę
                loadProducts();
            } else {
                throw new Error('Błąd dodawania produktu');
            }
        } catch (error) {
            showMessage('Błąd podczas dodawania produktu: ' + error.message, 'error');
        }
    }

    // Funkcja do usuwania produktu
    async function deleteProduct(productName) {
        if (!confirm(`Czy na pewno chcesz usunąć "${productName}"?`)) {
            return;
        }


        try {
            const response = await fetch(`${API_BASE_URL}/delete`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ name: productName })
            });

            if (response.ok) {
                showMessage('Produkt został usunięty pomyślnie!', 'success');
                loadProducts();
            } else {
                throw new Error('Błąd usuwania produktu');
            }
        } catch (error) {
            showMessage('Błąd podczas usuwania produktu: ' + error.message, 'error');
        }
    }

    // Funkcja do ładowania listy produktów
    async function loadProducts() {
        const loadingIndicator = document.getElementById('loadingIndicator');
        const productsList = document.getElementById('productsList');

        loadingIndicator.style.display = 'block';
        productsList.innerHTML = '';

        try {
            const response = await fetch(`${API_BASE_URL}/list`);

            if (!response.ok) {
                throw new Error('Błąd ładowania produktów');
            }

            const products = await response.json();
            loadingIndicator.style.display = 'none';

            if (products.length === 0) {
                productsList.innerHTML = `
                    <div class="empty-state">
                        <h3>Brak produktów na liście</h3>
                        <p>Dodaj pierwszy produkt używając formularza powyżej</p>
                    </div>
                `;
                return;
            }

            productsList.innerHTML = products.map(product => `
                <div class="product-item">
                    <div class="product-name">${product.name}</div>
                    <div class="product-quantity">${product.quantity} szt.</div>
                    <button class="btn btn-danger" onclick="deleteProduct('${product.name}')">
                        Usuń
                    </button>
                </div>
            `).join('');

        } catch (error) {
            loadingIndicator.style.display = 'none';
            productsList.innerHTML = `
                <div class="error">
                    Błąd ładowania produktów: ${error.message}
                    <br><small>Sprawdź czy serwer działa na porcie 8080</small>
                </div>
            `;
        }
    }

    // Obsługa Enter w formularzu
    document.addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            addProduct();
        }
    });

    // Załaduj produkty przy starcie strony
    document.addEventListener('DOMContentLoaded', function() {
        loadProducts();
    });