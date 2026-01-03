# eclipse-project

Appium Android Automation Test - MyDemoAppRN
📋 Project Overview
This Maven project demonstrates Appium + TestNG automation testing for the Sauce Labs MyDemoAppRN (React Native demo app).

Key Features:

Launches Android emulator programmatically

Installs and tests Android-MyDemoAppRN.1.3.0.build-244.apk

Uses Eclipse IDE, Appium Inspector for locators, Android Studio emulator

TestNG for test execution and assertions

🛠️ Tech Stack
Tool	Version	Purpose
Appium Java Client	9.2.3	Mobile automation
Selenium	4.21.0	WebDriver protocol
TestNG	7.10.2	Test framework
SLF4J	2.0.16	Logging
Android Emulator	API 36	Device simulation
📁 Project Structure
text
ca/
├── pom.xml                 # Maven dependencies
└── src/
    └── test/
        └── java/
            └── cse339ca1/
                └── ClassFile.java  # Main test class
📦 Prerequisites
1. Java & Maven
bash
java --version  # JDK 17+
mvn --version   # Maven 3.8+
2. Android SDK (via Android Studio)
Android Studio → SDK Manager → API Level 36

Add to PATH: platform-tools, emulator, tools

bash
adb --version
emulator -list-avds  # Should show "Medium_Phone_API_36.0"
3. Appium Server
bash
npm install -g appium
appium driver install uiautomator2
appium server  # Runs on http://127.0.0.1:4723
4. Eclipse IDE
TestNG plugin installed

Maven project imported

5. APK File
text
C:\Users\Aman\Downloads\Android-MyDemoAppRN.1.3.0.build-244.apk
🚀 Quick Start
1. Import Project
text
Eclipse → File → Import → Maven → Existing Maven Projects
→ Select pom.xml
2. Verify Emulator
bash
emulator -list-avds  # Medium_Phone_API_36.0
adb devices          # emulator-5554 (after boot)
3. Start Services
bash
# Terminal 1
appium server

# Terminal 2 (optional - for Inspector)
appium inspector
4. Run Tests
text
Eclipse → Right-click ClassFile.java → Run As → TestNG Test
Expected Output:

text
✅ Emulator booted.
✅ Driver session created, MyDemoAppRN launched.
✅ App running!
PASSED: testAppLaunch
🔍 Test Flow
@BeforeClass: Starts emulator → waits for boot → launches APK

testAppLaunch: Verifies driver + app loaded

@AfterClass: Quits session

🎯 Adding New Tests
Step 1: Use Appium Inspector
text
1. Start Inspector session with same capabilities
2. Interact with app → copy XPath/accessibility ID
3. Paste into test methods
Step 2: Example Test Pattern
java
@Test
public void testLogin() {
    driver.findElement(By.xpath("//*[@text='LOGIN']")).click();
    driver.findElement(By.accessibilityId("Username")).sendKeys("user@example.com");
    driver.findElement(By.accessibilityId("Password")).sendKeys("pass123");
    driver.findElement(By.xpath("//*[@text='LOGIN']")).click();
    
    WebElement successMsg = driver.findElement(By.xpath("//*[@text='Welcome']"));
    Assert.assertTrue(successMsg.isDisplayed());
}
⚙️ Configuration
Property	Value	Purpose
deviceId	emulator-5554	ADB device ID
appPath	C:\Users\Aman\Downloads\Android-MyDemoAppRN...	APK location
appiumPort	4723	Appium server
Customize in ClassFile.java:

java
String deviceId = "emulator-5554";  // adb devices
options.setApp("YOUR_APK_PATH");    // Update path
🔧 Troubleshooting
Issue	Solution
emulator-5554 not found	adb devices → update deviceId
APK not found	Verify file path exists
NoSuchElementException	Use Appium Inspector for real locators
Appium connection fail	appium server running? Port 4723 free?
Emulator timeout	Increase attempts=60 → 120
📊 Test Reports
text
Eclipse → TestNG view → Right-click → Show Reports
Generates HTML reports in test-output/

📝 POM Dependencies
xml
<!-- See pom.xml for full list -->
Appium Java Client: 9.2.3
Selenium: 4.21.0
TestNG: 7.10.2
🚀 Next Steps
✅ App launches (current)

🔄 Add login test → Appium Inspector

🔄 Add product catalog test

🔄 Parallel execution → multiple emulators

Author: CSE339 CA1 Project
Date: Jan 2026
Tools: Eclipse + Appium Inspector + Android Studio Emulator
