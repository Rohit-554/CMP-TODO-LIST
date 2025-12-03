import SwiftUI

@main
struct iOSApp: App {
    init() {
        Di_iosKt.doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}