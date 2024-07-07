
# stock-market-tracker
This app allows users to track stock market data using the Alpha Vantage API. It supports both Android (Kotlin with Jetpack Compose) and iOS (SwiftUI) platforms.

## Getting Started

To run this app, you'll need an API key from Alpha Vantage. Follow these steps to obtain your API key:

1. **Sign up or log in**: Create an account or log in to [Alpha Vantage](https://www.alphavantage.co/).

2. **Get your API Key**: Once logged in, navigate to the API documentation or your account settings to obtain your API key.

## Building and Running the App

### Android (Kotlin with Jetpack Compose)

1. Clone the repository:
   ```bash
   git clone https://github.com/jawad111/stock-market-tracker.git

2.  Open the project in Android Studio.
    
3.  Replace `"YOUR_API_KEY"` with your actual Alpha Vantage API key in the `local.properties` file:
    
    ```bash
    alphavantage.apikey=YOUR_API_KEY 
    
4.  Build and run the app on an Android emulator or device.
    

### iOS (SwiftUI)

1.  Clone the repository:
    
    ```bash
    git clone https://github.com/jawad111/stock-market-tracker.git
    
2.  Open the project in Xcode.
    
3.  Replace `"YOUR_API_KEY"` with your actual Alpha Vantage API key in the `Constants.swift` file:
    
    ```bash
    
    static let apiKey = "YOUR_API_KEY"
    
4.  Build and run the app on a simulator or device.
    

## Features

Search stocks and view the closed prices of stocks in Interactive Chart View.

-   **Android**: Built with MVVM architecture, Kotest with Mockito for testing, Kotlin, coroutines, and Jetpack Compose.
-   **iOS**: Built with MVVM architecture, SwiftUI, and integrates with the Alpha Vantage API.

## Screenshots
![Image](https://github.com/jawad111/stock-market-tracker/assets/33371507/f06b23fd-e107-4539-9396-e918aa7d3b20)
![Image (1)](https://github.com/jawad111/stock-market-tracker/assets/33371507/d98977d9-a37e-4226-a7a1-56b7047aa927)
![Image (2)](https://github.com/jawad111/stock-market-tracker/assets/33371507/f25dd61d-a50a-4a57-b2ed-997d1f41386a)
![Image (3)](https://github.com/jawad111/stock-market-tracker/assets/33371507/f86c8ebb-b106-481f-84a2-34af646e2559)
