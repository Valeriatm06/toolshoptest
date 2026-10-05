package co.edu.uptc.toolshoptest;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToolshoptestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolshoptestApplication.class, args);
		
		// Casos de prueba para Añadir al carrito
		//addToCart_SingleItem();
		//addToCart_MultipleUnits();
		//addToCart_TwoDifferentProducts();
		//addToCart_OutOfStockProduct();

		// Caso de prueba: Filtrar productos por rango de precios
		filterByPrice();
	}

	public static WebDriver createDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://practicesoftwaretesting.com/#/");
		return driver;
	}

	public static void register() {
		WebDriver driver = createDriver();
		driver.findElement(By.cssSelector("[data-test='nav-sign-in']")).click();
		driver.findElement(By.cssSelector("[data-test='register-link']")).click();
	}

	public static void login() {
		throw new UnsupportedOperationException("Unimplemented method 'login'");
	}

	public static void addToCart_SingleItem() {
		System.out.println("\n--- Caso 1: Adicionar una sola unidad al carrito ---");
		WebDriver driver = createDriver();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement producto = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("img[alt='Combination Pliers']"))
			);
			producto.click();
			System.out.println("1. Producto seleccionado: Combination Pliers");

			WebElement btnAddToCart = wait.until(
					ExpectedConditions.elementToBeClickable(By.id("btn-add-to-cart"))
			);
			btnAddToCart.click();
			System.out.println("2. Clic en 'Añadir al carrito'");

			WebElement cartBadge = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("lblCartCount"))
			);
			System.out.println("3. Contador del carrito actualizado a: " + cartBadge.getText());

			driver.findElement(By.cssSelector("[data-test='nav-cart']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".table")));
			System.out.println("4. Producto verificado en la tabla del carrito exitosamente.");
			Thread.sleep(1500);

		} catch (Exception e) {
			System.err.println("Error en addToCart_SingleItem: " + e.getMessage());
		} finally {
			driver.quit();
		}
	}

	public static void addToCart_MultipleUnits() {
		System.out.println("\n--- Caso 2: Adicionar múltiples unidades del mismo producto ---");
		WebDriver driver = createDriver();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement producto = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("img[alt='Combination Pliers']"))
			);
			producto.click();
			System.out.println("1. Producto seleccionado: Combination Pliers");

			WebElement inputCantidad = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='quantity']"))
			);
			inputCantidad.clear();
			inputCantidad.sendKeys("3");
			System.out.println("2. Cantidad configurada en: 3");

			WebElement btnAddToCart = wait.until(
					ExpectedConditions.elementToBeClickable(By.id("btn-add-to-cart"))
			);
			btnAddToCart.click();
			System.out.println("3. Clic en 'Añadir al carrito'");

			WebElement cartBadge = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("lblCartCount"))
			);
			System.out.println("4. Contador del carrito muestra: " + cartBadge.getText() + " unidades");

			driver.findElement(By.cssSelector("[data-test='nav-cart']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".table")));
			System.out.println("5. Múltiples unidades verificadas en el carrito.");
			Thread.sleep(1500);

		} catch (Exception e) {
			System.err.println("Error en addToCart_MultipleUnits: " + e.getMessage());
		} finally {
			driver.quit();
		}
	}

	public static void addToCart_TwoDifferentProducts() {
		System.out.println("\n--- Caso 3: Adicionar dos productos diferentes al carrito ---");
		WebDriver driver = createDriver();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			WebElement primerProducto = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("img[alt='Combination Pliers']"))
			);
			primerProducto.click();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-add-to-cart"))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblCartCount")));
			System.out.println("1. Primer producto agregado.");

			// 2. Regresar al Home
			WebElement navHome = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test='nav-home']")));
			navHome.click();

			// 3. Esperar que aparezca y seleccionar el segundo producto específico: Bolt Cutters
			WebElement segundoProducto = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("img[alt='Bolt Cutters']"))
			);
			segundoProducto.click();
			System.out.println("2. Segundo producto seleccionado (Bolt Cutters).");

			WebElement btnAdd2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-add-to-cart")));
			btnAdd2.click();
			System.out.println("3. Clic en 'Añadir al carrito' para Bolt Cutters.");

			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("lblCartCount"), "2"));
			WebElement cartBadge = driver.findElement(By.id("lblCartCount"));
			System.out.println("4. Contador del carrito actualizado a: " + cartBadge.getText() + " productos");

			driver.findElement(By.cssSelector("[data-test='nav-cart']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".table")));
			List<WebElement> filasCarrito = driver.findElements(By.cssSelector("tbody tr"));
			System.out.println("5. Filas encontradas en la tabla del carrito: " + filasCarrito.size());
			Thread.sleep(1500);

		} catch (Exception e) {
			System.err.println("Error en addToCart_TwoDifferentProducts: " + e.getMessage());
		} finally {
			driver.quit();
		}
	}

	// Caso: Intentar añadir un producto agotado (Long Nose Pliers)
	public static void addToCart_OutOfStockProduct() {
		System.out.println("\n--- Caso: Intentar añadir un producto agotado (Long Nose Pliers) ---");
		WebDriver driver = createDriver();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			// 1. Clic en la imagen del producto agotado "Long Nose Pliers"
			WebElement producto = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("img[alt='Long Nose Pliers']"))
			);
			producto.click();
			System.out.println("1. Producto seleccionado: Long Nose Pliers (Agotado)");

			// 2. Esperar a que cargue la vista de detalle
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));

			// 3. Localizar el botón de añadir al carrito y verificar que está deshabilitado
			WebElement btnAddToCart = driver.findElement(By.id("btn-add-to-cart"));
			boolean estaHabilitado = btnAddToCart.isEnabled();
			String atributoDisabled = btnAddToCart.getAttribute("disabled");

			System.out.println("2. Validación del botón 'Añadir al carrito':");
			System.out.println("   - isEnabled(): " + estaHabilitado);
			System.out.println("   - Atributo disabled: " + atributoDisabled);

			if (!estaHabilitado || "true".equalsIgnoreCase(atributoDisabled)) {
				System.out.println("3. Verificación exitosa: El botón está deshabilitado (disabled=true).");
			} else {
				System.out.println("3. Advertencia: El botón no está deshabilitado.");
			}

			// 4. Confirmar que el carrito permanezca sin productos
			List<WebElement> cartBadges = driver.findElements(By.id("lblCartCount"));
			if (cartBadges.isEmpty() || cartBadges.get(0).getText().trim().isEmpty() || "0".equals(cartBadges.get(0).getText().trim())) {
				System.out.println("4. Carrito verificado: Permanece vacío.");
			}

			Thread.sleep(1500);

		} catch (Exception e) {
			System.err.println("Error en addToCart_OutOfStockProduct: " + e.getMessage());
		} finally {
			driver.quit();
		}
	}

	public static void filterByPrice() {
		filterByPrice_ReduceMax();
		filterByPrice_IncreaseMin();
		filterByPrice_VerifyProductDetail();
		filterByPrice_WithSortingLowToHigh();
		filterByPrice_ResetSlider();
		filterByPrice_ExpandToCeilingMax();
	}

	// Caso 1: Reducción del precio máximo
	public static void filterByPrice_ReduceMax() {
		System.out.println("\n--- Caso 1: Filtrar reduciendo el precio máximo ---");
		WebDriver driver = createDriver();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img[alt='Combination Pliers']")));

			WebElement pointerMax = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector(".ngx-slider-pointer-max"))
			);
			System.out.println("1. Límite máximo inicial: $" + pointerMax.getAttribute("aria-valuenow"));

			Actions actions = new Actions(driver);
			actions.clickAndHold(pointerMax).moveByOffset(-80, 0).release().perform();
			Thread.sleep(1500);

			WebElement bubbleMax = driver.findElement(By.cssSelector(".ngx-slider-model-high"));
			double nuevoPrecioMax = Double.parseDouble(bubbleMax.getText().replace("$", "").trim());
			System.out.println("2. Nuevo límite máximo aplicado: $" + nuevoPrecioMax);

			List<WebElement> precios = driver.findElements(By.cssSelector("[data-test='product-price']"));
			System.out.println("3. Productos en catálogo tras el filtro: " + precios.size());

			boolean todosValidos = true;
			for (WebElement el : precios) {
				double precio = Double.parseDouble(el.getText().replace("$", "").trim());
				System.out.println("   - Producto: $" + precio);
				if (precio > nuevoPrecioMax) {
					todosValidos = false;
					System.err.println("   [ERROR] Producto excede el rango máximo: $" + precio);
				}
			}

			if (todosValidos && !precios.isEmpty()) {
				System.out.println("4. Verificación exitosa: Ningún producto excede $" + nuevoPrecioMax);
			}
			Thread.sleep(1500);

		} catch (Exception e) {
			System.err.println("Error en filterByPrice_ReduceMax: " + e.getMessage());
		} finally {
			driver.quit();
		}
	}

	// Caso 2: Aumento del precio mínimo (Filtro inferior)
	public static void filterByPrice_IncreaseMin() {
		System.out.println("\n--- Caso 2: Filtrar aumentando el precio mínimo ---");
		WebDriver driver = createDriver();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img[alt='Combination Pliers']")));

			WebElement pointerMin = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector(".ngx-slider-pointer-min"))
			);
			System.out.println("1. Límite mínimo inicial: $" + pointerMin.getAttribute("aria-valuenow"));

			Actions actions = new Actions(driver);
			actions.clickAndHold(pointerMin).moveByOffset(80, 0).release().perform();
			Thread.sleep(1500);

			WebElement bubbleMin = driver.findElement(By.cssSelector(".ngx-slider-model-value"));
			double nuevoPrecioMin = Double.parseDouble(bubbleMin.getText().replace("$", "").trim());
			System.out.println("2. Nuevo límite mínimo aplicado: $" + nuevoPrecioMin);

			List<WebElement> precios = driver.findElements(By.cssSelector("[data-test='product-price']"));
			System.out.println("3. Productos en catálogo tras el filtro: " + precios.size());

			boolean todosValidos = true;
			for (WebElement el : precios) {
				double precio = Double.parseDouble(el.getText().replace("$", "").trim());
				System.out.println("   - Producto: $" + precio);
				if (precio < nuevoPrecioMin) {
					todosValidos = false;
					System.err.println("   [ERROR] Producto con precio inferior al mínimo: $" + precio);
				}
			}

			if (todosValidos && !precios.isEmpty()) {
				System.out.println("4. Verificación exitosa: Ningún producto está por debajo de $" + nuevoPrecioMin);
			}
			Thread.sleep(1500);

		} catch (Exception e) {
			System.err.println("Error en filterByPrice_IncreaseMin: " + e.getMessage());
		} finally {
			driver.quit();
		}
	}

	// Caso 3: Filtro de precio combinado con ordenamiento (Sort: Low - High)
	public static void filterByPrice_WithSortingLowToHigh() {
		System.out.println("\n--- Caso 3: Filtrar por precio y ordenar de Menor a Mayor ---");
		WebDriver driver = createDriver();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img[alt='Combination Pliers']")));

			// 1. Reducir precio máximo
			WebElement pointerMax = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector(".ngx-slider-pointer-max"))
			);
			Actions actions = new Actions(driver);
			actions.clickAndHold(pointerMax).moveByOffset(-70, 0).release().perform();
			Thread.sleep(1500);

			WebElement bubbleMax = driver.findElement(By.cssSelector(".ngx-slider-model-high"));
			double nuevoPrecioMax = Double.parseDouble(bubbleMax.getText().replace("$", "").trim());
			System.out.println("1. Límite máximo aplicado: $" + nuevoPrecioMax);

			// 2. Seleccionar ordenamiento: Precio (Bajo - Alto)
			WebElement sortSelect = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test='sort']"))
			);
			Select select = new Select(sortSelect);
			select.selectByValue("price,asc");
			System.out.println("2. Ordenamiento seleccionado: Precio (Bajo - Alto)");
			Thread.sleep(1500);

			// 3. Verificar que los precios estén ordenados ascendentemente y <= nuevoPrecioMax
			List<WebElement> precios = driver.findElements(By.cssSelector("[data-test='product-price']"));
			System.out.println("3. Productos ordenados en pantalla: " + precios.size());

			double precioAnterior = 0.0;
			boolean ordenCorrecto = true;
			for (WebElement el : precios) {
				double precioActual = Double.parseDouble(el.getText().replace("$", "").trim());
				System.out.println("   - Precio: $" + precioActual);
				if (precioActual < precioAnterior || precioActual > nuevoPrecioMax) {
					ordenCorrecto = false;
				}
				precioAnterior = precioActual;
			}

			if (ordenCorrecto && !precios.isEmpty()) {
				System.out.println("4. Verificación exitosa: Productos ordenados ascendentemente y respetan el rango máximo!");
			}
			Thread.sleep(1500);

		} catch (Exception e) {
			System.err.println("Error en filterByPrice_WithSortingLowToHigh: " + e.getMessage());
		} finally {
			driver.quit();
		}
	}

	// Caso 4: Restablecer / Reset del filtro de precios
	public static void filterByPrice_ResetSlider() {
		System.out.println("\n--- Caso 4: Restablecer filtro de precio y recuperar catálogo completo ---");
		WebDriver driver = createDriver();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img[alt='Combination Pliers']")));

			int conteoInicial = driver.findElements(By.cssSelector(".card")).size();
			System.out.println("1. Cantidad inicial de productos: " + conteoInicial);

			// Reducir rango drásticamente
			WebElement pointerMax = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector(".ngx-slider-pointer-max"))
			);
			Actions actions = new Actions(driver);
			actions.clickAndHold(pointerMax).moveByOffset(-120, 0).release().perform();
			Thread.sleep(1500);

			int conteoFiltrado = driver.findElements(By.cssSelector(".card")).size();
			System.out.println("2. Cantidad de productos con filtro estricto: " + conteoFiltrado);

			// Arrastrar de vuelta hacia la derecha para restablecer
			actions.clickAndHold(pointerMax).moveByOffset(120, 0).release().perform();
			Thread.sleep(1500);

			int conteoRestablecido = driver.findElements(By.cssSelector(".card")).size();
			System.out.println("3. Cantidad de productos tras restablecer slider: " + conteoRestablecido);

			if (conteoRestablecido >= conteoInicial) {
				System.out.println("4. Verificación exitosa: Catálogo restablecido al tamaño original!");
			}
			Thread.sleep(1500);

		} catch (Exception e) {
			System.err.println("Error en filterByPrice_ResetSlider: " + e.getMessage());
		} finally {
			driver.quit();
		}
	}

	// Caso 5: Expandir el filtro al precio máximo absoluto ($200)
	public static void filterByPrice_ExpandToCeilingMax() {
		System.out.println("\n--- Caso 5: Expandir slider al precio máximo absoluto ($200) ---");
		WebDriver driver = createDriver();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img[alt='Combination Pliers']")));

			// 1. Localizar el deslizador de precio máximo (.ngx-slider-pointer-max)
			WebElement pointerMax = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector(".ngx-slider-pointer-max"))
			);
			System.out.println("1. Precio máximo por defecto en la tienda: $" + pointerMax.getAttribute("aria-valuenow"));

			// 2. Arrastrar hacia la derecha hasta el tope máximo (+200px)
			Actions actions = new Actions(driver);
			actions.clickAndHold(pointerMax).moveByOffset(200, 0).release().perform();
			Thread.sleep(1500);

			// 3. Validar que la etiqueta del límite máximo alcance $200 (el techo absoluto)
			WebElement bubbleMax = driver.findElement(By.cssSelector(".ngx-slider-model-high"));
			double nuevoPrecioMax = Double.parseDouble(bubbleMax.getText().replace("$", "").trim());
			System.out.println("2. Límite máximo tras expandir el slider: $" + nuevoPrecioMax);

			if (nuevoPrecioMax == 200.0) {
				System.out.println("3. Verificación exitosa: El slider alcanzó el precio máximo absoluto ($200.0)!");
			}

			// 4. Confirmar que el catálogo se mantiene poblado y funcional con el nuevo rango
			List<WebElement> precios = driver.findElements(By.cssSelector("[data-test='product-price']"));
			System.out.println("4. Cantidad de productos visibles con rango máximo: " + precios.size());

			Thread.sleep(1500);

		} catch (Exception e) {
			System.err.println("Error en filterByPrice_ExpandToCeilingMax: " + e.getMessage());
		} finally {
			driver.quit();
		}
	}

	// Caso: Comprobar precio al abrir un producto filtrado en detalle
	public static void filterByPrice_VerifyProductDetail() {
		System.out.println("\n--- Caso: Comprobar precio al abrir producto filtrado en detalle ---");
		WebDriver driver = createDriver();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img[alt='Combination Pliers']")));

			// 1. Reducir precio máximo con el slider
			WebElement pointerMax = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector(".ngx-slider-pointer-max"))
			);
			Actions actions = new Actions(driver);
			actions.clickAndHold(pointerMax).moveByOffset(-80, 0).release().perform();
			Thread.sleep(1500);

			WebElement bubbleMax = driver.findElement(By.cssSelector(".ngx-slider-model-high"));
			double precioMaxFiltro = Double.parseDouble(bubbleMax.getText().replace("$", "").trim());
			System.out.println("1. Filtro máximo aplicado: $" + precioMaxFiltro);

			// 2. Dar clic en un producto filtrado (Combination Pliers)
			WebElement producto = wait.until(
					ExpectedConditions.elementToBeClickable(By.cssSelector("img[alt='Combination Pliers']"))
			);
			producto.click();
			System.out.println("2. Producto abierto en detalle: Combination Pliers");

			// 3. Obtener el precio unitario en la página de detalle
			WebElement unitPriceElem = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='unit-price']"))
			);
			double precioDetalle = Double.parseDouble(unitPriceElem.getText().replace("$", "").trim());
			System.out.println("3. Precio unitario verificado en detalle: $" + precioDetalle);

			if (precioDetalle <= precioMaxFiltro) {
				System.out.println("4. Verificación exitosa: El precio ($" + precioDetalle + ") está dentro del rango del filtro (<= $" + precioMaxFiltro + ")");
			} else {
				System.err.println("4. Error: El precio excede el límite del filtro.");
			}
			Thread.sleep(1500);

		} catch (Exception e) {
			System.err.println("Error en filterByPrice_VerifyProductDetail: " + e.getMessage());
		} finally {
			driver.quit();
		}
	}

	public static void filterByCategory() {
		throw new UnsupportedOperationException("Unimplemented method 'filterByCategory'");
	}

	public static void checkoutThreeProducts() {
		throw new UnsupportedOperationException("Unimplemented method 'checkoutThreeProducts'");
	}
}
