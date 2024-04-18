package ibf.paf.inclass25;

import java.io.Console;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;

import ibf.paf.inclass25.services.MessageService;

@SpringBootApplication
@Async
public class Inclass25Application implements CommandLineRunner {

	@Autowired
	MessageService msgSvc;

	public static void main(String[] args) {
		SpringApplication.run(Inclass25Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("----------- Delaying execution...");
        Thread.sleep(1000); // Sleep for 2 seconds (adjust the time as needed)
        System.out.println("----------- Executing the command line runner...");

		Scanner scanner = new Scanner(System.in);
		boolean stop = false;
		
		while (!stop) {
            // Read a message from the console
			Thread.sleep(10000);
			System.out.print("Your message: ");
			String message = scanner.nextLine();
	
            msgSvc.lpush("TheRealAlan", message);

		
            // Check if the user wants to exit
            if ("exit".equalsIgnoreCase(message.trim())) {
				stop = true;
                System.out.println("Exiting the program...");
                break; // Exit the loop
            }
		}
		scanner.close();
	}
}
