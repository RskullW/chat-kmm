import iosExport
import SwiftUI

extension ResourcesImageResource {
    func toImage() -> Image {
        if let uiImage = self.toUIImage() {
            return Image(uiImage: uiImage)
        } else {
            return Image("placeholder")
        }
    }
}
