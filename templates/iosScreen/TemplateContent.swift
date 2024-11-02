import iosExport
import SwiftUI

struct TemplateContent: View {
    var body: some View {
        VStack {
            Text("Hello world!")
                .font(.system(size: 24))
                .fontWeight(.bold)
                .padding(.top, 24)
        }
        .font(.largeTitle)
        .padding()
    }
}

struct TemplateContent_Previews: PreviewProvider {
    static var previews: some View {
        TemplateContent()
    }
}

