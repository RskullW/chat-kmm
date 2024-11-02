import SwiftUI
import iosExport

@main
struct iOSApp: App {
    @StateObject private var viewModel: RootViewModelWrapper
    @State private var currentScreen: AnyView
    @State private var navigationPath = NavigationPath()
    
    init() {
        InitKoinKt.startKoin(koinAppDeclaration: { _ in })

        _viewModel = StateObject(wrappedValue: RootViewModelWrapper())
        currentScreen = AnyView(ZStack {})//AnyView(SplashScreen())
    }
    var body: some Scene {
        WindowGroup {
            NavigationStack(path: $navigationPath) {
                ZStack {
                    currentScreen
                        .transition(.customSlide)
                        .animation(.easeInOut, value: UUID())
                }
                
                .onChange(of: viewModel.screen) { oldState, newState in
                    withAnimation {
                        handleScreenChange(oldScreen: oldState, newScreen: newState)
                    }
                }
                .navigationDestination(for: iosExport.Screen.self) { screen in
                    screen.view()
                        .navigationBarBackButtonHidden(true)
                }
                
            }
        }
    }
    
    private func handleScreenChange(oldScreen: iosExport.Screen?, newScreen: iosExport.Screen?) {
        if let screen = newScreen {
           if viewModel.isClearStack {
                let newView = screen.view()
                currentScreen = AnyView(newView)
                navigationPath = NavigationPath()
            } else {
                navigationPath.append(screen)
            }
        } else {
            if viewModel.areThereOtherOpenScreen() {
                if !navigationPath.isEmpty {
                    navigationPath.removeLast()
                    viewModel.removeLast()
                }
            }
        }
    }
}
