import SwiftUI
import iosExport

class Colors {
    var blue: SwiftUI.Color { fatalError("Must override") }
    var gray: SwiftUI.Color { fatalError("Must override") }
    var primary: SwiftUI.Color { fatalError("Must override") }
}

class LightColors: Colors {
    override var blue: SwiftUI.Color { MultiplatformResource.colors.shared.blue.getColor() }
    override var gray: SwiftUI.Color { MultiplatformResource.colors.shared.gray.getColor() }
    override var primary: SwiftUI.Color { MultiplatformResource.colors.shared.primaryColor.getColor() }
}

class DarkColors: Colors {
    override var blue: SwiftUI.Color { MultiplatformResource.colors.shared.blue.getColor() }
    override var gray: SwiftUI.Color { MultiplatformResource.colors.shared.gray.getColor() }
    override var primary: SwiftUI.Color { MultiplatformResource.colors.shared.primaryColor.getColor() }
}
