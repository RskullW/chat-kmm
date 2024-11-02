import SwiftUI
import iosExport

func addObserver<T>(_ observable: iosExport.StateFlow<T>, handler: @escaping (T?) -> Void) {
    observable.addObserver { value in
        DispatchQueue.main.async {
            handler(value)
        }
    }
}
