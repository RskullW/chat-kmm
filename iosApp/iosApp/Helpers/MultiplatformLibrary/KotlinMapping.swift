import SwiftUI
import iosExport

extension KotlinInt? {
    func toInt() -> Int {
        return self?.intValue ?? -1
    }
}
