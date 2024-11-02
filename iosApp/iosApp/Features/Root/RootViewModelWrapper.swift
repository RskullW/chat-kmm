import iosExport
import SwiftUI

class RootViewModelWrapper: ObservableObject {
    @Published var screen: iosExport.Screen? = .splash
    @Published var isClearStack: Bool = false
    
    private var viewModel: RootViewModel
    
    init() {
        InitKoinKt.startKoin(koinAppDeclaration: { _ in })
        self.viewModel = inject()
        
        viewModel.screen.addObserver { [weak self] screen in
            DispatchQueue.main.async {
                self?.isClearStack = self?.viewModel.isClearStack ?? false
                self?.screen = screen
            }
        }
    }
    
    public func finishScreen() {
        viewModel.finishScreen()
    }
    
    public func areThereOtherOpenScreen() -> Bool {
        return viewModel.areThereOtherOpenScreens()
    }
    
    public func removeLast() {
        viewModel.removeLast()
    }
}
