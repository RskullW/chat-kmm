import SwiftUI
import iosExport

extension iosExport.Screen {
    @ViewBuilder
    func view() -> some View {
        
        switch self {
        case iosExport.Screen.authorization:
            ZStack {} //  AuthorizationScreen()
        case iosExport.Screen.main:
            ZStack {} //MainScreen()
        case iosExport.Screen.splash:
            ZStack {} //SplashScreen()
        default:
            ZStack {} //AuthorizationScreen()
        }
    }
}
