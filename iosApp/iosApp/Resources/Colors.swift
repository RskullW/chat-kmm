import SwiftUI
import iosExport

class Colors {
    var primary: SwiftUI.Color { fatalError("Must override") }
}

class LightColors: Colors {
    override var primary: SwiftUI.Color { MultiplatformResource.colors.shared.primaryColor.getColor() }
}

class DarkColors: Colors {
    override var primary: SwiftUI.Color { MultiplatformResource.colors.shared.primaryColor.getColor() }
}
