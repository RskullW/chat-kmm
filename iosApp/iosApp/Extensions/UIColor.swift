import SwiftUI
import iosExport

extension ResourcesColorResource {
    func getUIColor() -> UIColor {
        guard let color = UIColor(named: name, in: bundle, compatibleWith: nil) else {
            return UIColor.clear
        }
        return color
    }
    
    
    func getColor() -> SwiftUI.Color {
        return SwiftUI.Color(getUIColor())
    }
}

extension UIColor {
    convenience init(hex: String) {
        var hexSanitized = hex.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()
        if hexSanitized.hasPrefix("#") {
            hexSanitized.removeFirst()
        }
        let scanner = Scanner(string: hexSanitized)
        var rgb: UInt64 = 0
        scanner.scanHexInt64(&rgb)
        let r = CGFloat((rgb >> 16) & 0xFF) / 255.0
        let g = CGFloat((rgb >> 8) & 0xFF) / 255.0
        let b = CGFloat(rgb & 0xFF) / 255.0
        self.init(red: r, green: g, blue: b, alpha: 1.0)
    }
}
