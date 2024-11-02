import iosExport
import SwiftUI

extension ResourcesFontResource {
    func toFont(size: Double) -> Font {
        return Font(CTFontCreateWithName(self.fontName as CFString, size, nil))
    }
}

extension SwiftUI.Font {
    static func regular(size: Double = 14.0) -> Font {
        let uiFont = MultiplatformResource.fonts()[keyPath: \.regular].uiFont(withSize: size)
        
        return Font.init(uiFont)
    }
    
    static func italic(size: Double = 14.0) -> Font {
        let uiFont = MultiplatformResource.fonts()[keyPath: \.regular].uiFont(withSize: size)
        
        return Font.init(uiFont)
    }
    
    static func bold(size: Double = 14.0) -> Font {
        let uiFont = MultiplatformResource.fonts()[keyPath: \.regular].uiFont(withSize: size)
        
        return Font.init(uiFont)
    }
    
    static func semibold(size: Double = 14.0) -> Font {
        let uiFont = MultiplatformResource.fonts()[keyPath: \.regular].uiFont(withSize: size)
        
        return Font.init(uiFont)
    }
    
    static func medium(size: Double = 14.0) -> Font {
        let uiFont = MultiplatformResource.fonts()[keyPath: \.regular].uiFont(withSize: size)
        
        return Font.init(uiFont)
    }
}
