root {
    'groovy.swing.SwingBuilder' {
        controller = ['Threading']
        view = '*'
    }
    'griffon.app.ApplicationBuilder' {
        view = '*'
    }
}
root.'TrayBuilderGriffonAddon'.addon=true

root.'I18nGriffonAddon'.addon=true

root.'griffon.builder.swingxtras.SwingxtrasBuilder'.view = '*'

root.'MiglayoutGriffonAddon'.addon=true

root.'griffon.builder.trident.TridentBuilder'.view = '*'

root.'EffectsGriffonAddon'.addon=true
