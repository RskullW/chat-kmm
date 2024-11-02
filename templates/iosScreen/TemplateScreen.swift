import SwiftUI
import iosExport

struct TemplateScreen: View {
    
    private var viewModel: SplashViewModel {
        inject()
    }
    private var rootViewModel: RootViewModel {
        inject()
    }
    
    var body: some View {
        TemplateContent()
            .onAppear {
                initialize()
            }
            
    }
    
    private func initialize() {
       /* viewModel.data.addObserver { [self] value in
           
        }*/
    }
}

struct TemplateScreen_Previews: PreviewProvider {
    static var previews: some View {
        TemplateScreen()
    }
}

