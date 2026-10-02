package co.edu.uptc.toolshoptest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@SpringBootApplication
public class ToolshoptestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolshoptestApplication.class, args);
		register();
        login();
        addToCart();
        filterByPrice();
        filterByCategory();
        checkoutThreeProducts();
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

	private static void login() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'login'");
	}

	private static void addToCart() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addToCart'");
	}

	private static void filterByPrice() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'filterByPrice'");
	}

	private static void filterByCategory() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'filterByCategory'");
	}

	private static void checkoutThreeProducts() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'checkoutThreeProducts'");
	}

	

	

	

	

	


}
