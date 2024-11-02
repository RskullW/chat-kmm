import SwiftUI
import iosExport

extension ResourcesStringResource {
    func toString() -> String {
        return toStringDesc().localized()
    }
}

extension Optional where Wrapped == NSString {
    func toString() -> String {
        return self?.toString() ?? ""
    }
    
    func toNullable() -> Nullable<NSString> {
        return Nullable(value: self)
    }
}

extension Optional where Wrapped == String {
    func toNullable() -> Nullable<NSString> {
        guard let string = self else { return Nullable(value: nil) }
        return NSString(string: string).toNullable()
    }
}

extension NSString {
    func toString() -> String {
        return self as String
    }
    
    func toNullable() -> Nullable<NSString> {
        return Nullable(value: self)
    }
}


extension String {
    func toNullable() -> Nullable<NSString> {
        return NSString(string: self).toNullable()
    }
}
