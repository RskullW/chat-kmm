import iosExport
import SwiftUI

struct Typography {
    var main: TitleTypography { TitleTypography() }
}

struct TitleTypography {
    var title: Font { .semibold(size: 20) }
    var main: Font { .regular(size: 14) }
    var buttonText: Font { .semibold(size: 16) }
    var inputText: Font { .regular(size: 14) }
    var hintText: Font { .regular(size: 14) }
}
