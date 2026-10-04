package co.edu.uptc.toolshoptest;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToolshoptestApplication {

	private static final String USER_EMAIL = "customer2@practicesoftwaretesting.com";
    private static final String USER_PASSWORD = "welcome01";

	public static void main(String[] args) {
		SpringApplication.run(ToolshoptestApplication.class, args);
		//register();
        login();
        //addToCart();
        //filterByPrice();
        filterByCategory();
        //checkoutThreeProducts();
	}


	public static WebDriver createDriver(){
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		// 2. Darle hasta 10 segundos para encontrar cualquier elemento antes de dar error
		driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
		driver.get("https://practicesoftwaretesting.com/#/");
		return driver;
	}

	public static void register() {
		WebDriver driver = createDriver();
		driver.findElement(By.cssSelector("[data-test='nav-sign-in']")).click();
		driver.findElement(By.cssSelector("[data-test='register-link']")).click();
	}

	public static void login() {
        System.out.println("INICIANDO PRUEBA: LOGIN");
        WebDriver driver = createDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // Navegar a la pantalla de Login (driver ya está en la URL base)
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test='nav-sign-in']"))).click();

            // Diligenciar campos de inicio de sesión
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='email']"))).sendKeys(USER_EMAIL);
            driver.findElement(By.cssSelector("[data-test='password']")).sendKeys(USER_PASSWORD);
            driver.findElement(By.cssSelector("[data-test='login-submit']")).click();

            // Validar ingreso exitoso a la cuenta
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("account"),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='nav-menu']")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='page-title']"))
            ));

            System.out.println("login exitoso para el usuario: " + USER_EMAIL);
            Thread.sleep(1000);

        } catch (Exception e) {
            System.err.println("Error en la prueba de login: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

	private static void addToCart() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addToCart'");
	}

	private static void filterByPrice() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'filterByPrice'");
	}

	public static void filterByCategory() {
        System.out.println("INICIANDO PRUEBA: FILTRAR PRODUCTOS POR CATEGORÍA");
        WebDriver driver = createDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Esperar la carga de tarjetas de productos (driver ya está en el catálogo principal)
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.card")));
            int productosIniciales = driver.findElements(By.cssSelector("a.card")).size();
            System.out.println("Cantidad de productos iniciales: " + productosIniciales);

            // Filtrar por categoría desde el menú lateral o la barra de navegación
            List<WebElement> categoryCheckboxes = driver.findElements(
                By.cssSelector("fieldset input[type='checkbox'], input[data-test*='category']")
            );

            if (!categoryCheckboxes.isEmpty()) {
                // Seleccionar el primer filtro de categoría disponible en el panel izquierdo
                WebElement categoryOption = categoryCheckboxes.get(0);
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", categoryOption);
                
                if (!categoryOption.isSelected()) {
                    js.executeScript("arguments[0].click();", categoryOption);
                    js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", categoryOption);
                }
            } else {
                WebElement navCategories = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("[data-test='nav-categories']")
                ));
                js.executeScript("arguments[0].click();", navCategories);

                WebElement categoryItem = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".dropdown-menu a, [data-test*='category']")
                ));
                js.executeScript("arguments[0].click();", categoryItem);
            }

            // Esperar actualización de la lista filtrada
            Thread.sleep(2000);

            // Verificación de resultados tras el filtrado
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.card")));
            List<WebElement> productosFiltrados = driver.findElements(By.cssSelector("a.card"));

            if (!productosFiltrados.isEmpty()) {
                System.out.println("Filtrado exitoso. Productos mostrados en la categoría: " + productosFiltrados.size());
            } else {
                System.out.println("Se aplicó el filtro pero no se encontraron productos en esta categoría.");
            }

        } catch (Exception e) {
            System.err.println("Error en la prueba de filtrado por categoría: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

	public static void checkoutThreeProducts() {
       
    }

}
