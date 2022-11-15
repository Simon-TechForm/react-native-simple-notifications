require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name            = "RNSimpleNotifications"
  s.version         = package["version"]
  s.summary         = package["description"]
  s.homepage        = package["homepage"]
  s.license         = package["license"]
  s.author          = package["author"]
  s.platforms       = { :ios => "11.0" }
  s.source          = { :git => package["homepage"], :tag => "#{s.version}" }

  s.source_files    = "ios/**/*.{h,m,mm,swift}"

  s.dependency "React-Core"
end