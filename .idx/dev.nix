# To learn more about how to use Nix to configure your environment
# see: https://firebase.google.com/docs/studio/customize-workspace
{ pkgs, ... }: {
  # Fact-Check: Stable 24.05 channel Android support ke liye bilkul sahi hai.
  channel = "stable-24.05";

  packages = [
    pkgs.jdk17
    pkgs.gradle
    pkgs.android-tools # Terminal mein adb aur fastboot commands ke liye
  ];

  # Environment Variables
  env = {
    JAVA_HOME = "${pkgs.jdk17}";
    # Fact-Check: IDX mein ANDROID_HOME ko nix profile ke through set karna best hai
    ANDROID_HOME = "/home/user/Android/Sdk"; 
  };

  idx = {
    # Android development ke liye zaroori extensions
    extensions = [
      "vscjava.vscode-java-pack"
      "vscjava.vscode-gradle"
    ];

    # Fact-Check: Previews section mein 'manager = "android"' likhna zaroori hai
    # Isse IDX automatically Android Emulator aur SDK tools load karta hai.
    previews = {
      enable = true;
      previews = {
        android = {
          # Ye command build check karega
          command = ["./gradlew" "assembleDebug"];
          manager = "android";
        };
      };
    };

    # Workspace hooks: Ye tab chalte hain jab aapka project load hota hai
    workspace = {
      onCreate = {
        # Pehli baar setup hone par licenses accept karne ki koshish karega
        accept-licenses = "yes | sdkmanager --licenses || true";
      };
    };
  };
}