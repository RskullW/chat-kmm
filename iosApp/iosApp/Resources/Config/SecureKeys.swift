import Foundation

class BaseENV {
    
    let dict: NSDictionary
    
    init(resourceName: String) {
        guard let filePath = Bundle.main.path(forResource: resourceName, ofType: "plist"), let plist = NSDictionary(contentsOfFile: filePath) else {
            fatalError("Couldn't find file plist")
        }
        self.dict = plist
    }
}

protocol APIKeyable {
    var URL_API_KEY: String { get }

}

class APIENV: BaseENV, APIKeyable {
    init() {
        super.init(resourceName: "SecureKeys")
    }
    
    var URL_API_KEY: String {
        dict.object(forKey: "URL_API_KEY") as? String ?? ""
    }
}

var ENV: APIKeyable {
    return APIENV()
}
